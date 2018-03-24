package com.example.atul_.eatit;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atul_.eatit.Common.*;
import com.example.atul_.eatit.Interface.ItemClickListener;
import com.example.atul_.eatit.Service.ListenOrder;
import com.example.atul_.eatit.ViewHolder.MenuViewHolder;
import com.example.atul_.eatit.model.Category;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;

import static com.example.atul_.eatit.Permission.requestForPermission;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseDatabase database;
    DatabaseReference category;
    TextView txtFullName;
    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

     FirebaseRecyclerAdapter<Category, MenuViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        requestForPermission(Home.this,0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        toolbar.setBackgroundColor(Color.parseColor("#39796b"));setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();
        category = database.getReference("Category");

        Paper.init(this);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cartIntent = new Intent(Home.this,Cart.class);
                startActivity(cartIntent);

            }



        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        txtFullName=(TextView)findViewById(R.id.txtFullName) ;
        txtFullName=(TextView)headerView.findViewById(R.id.txtFullName);
      //  txtFullName.setText(Common.currentUser.getName());



        recycler_menu=(RecyclerView)findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
       recycler_menu.setLayoutManager(layoutManager);
        //recycler_menu.setLayoutManager(new GridLayoutManager(this,2));


        if(Common.isConnectedToInternet(this))


            loadMenu();
        else {
            Toast.makeText(this, "Please check your connection", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent i=new Intent(Home.this,ListenOrder.class);
        startService(i);


    }


    private void loadMenu() {
        adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,R.layout.menu_item,MenuViewHolder.class,category) {
            protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {
                viewHolder.txtMenuName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.imageView);
                //final Category clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //        Toast.makeText(Home.this, ""+clickItem.getName(), Toast.LENGTH_SHORT).show();
                        Intent foodList = new Intent(Home.this, FoodList.class);
                        foodList.putExtra("CategoryId",adapter.getRef(position).getKey());
                        startActivity(foodList);
                    }
                });
            }
        };
        recycler_menu.setAdapter(adapter);
    }


            public void onBackPressed() {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    super.onBackPressed();
                }
            }


            public boolean onCreateOptionsMenu(Menu menu) {
                // Inflate the menu; this adds items to the action bar if it is present.
                MenuInflater inflater=getMenuInflater();
                inflater.inflate(R.menu.activity_home_drawer, menu);
                return true;
            }

            public boolean onOptionsItemSelected(MenuItem item) {

                return super.onOptionsItemSelected(item);
            }

           // @SuppressWarnings("StatementWithEmptyBody")

            public boolean onNavigationItemSelected(MenuItem item) {
                // Handle navigation view item clicks here.
                int id = item.getItemId();
                 if (id == R.id.nav_menu) {
                    // Handle the camera action
                } else if (id == R.id.nav_cart) {
                     Intent cartIntent=new Intent(Home.this,Cart.class);
                     startActivity(cartIntent);

                } else if (id == R.id.nav_order) {
                     Intent orderIntent=new Intent(Home.this,OrderStatus.class);
                     startActivity(orderIntent);


                }
                 else if (id == R.id.nav_fav) {
                     Intent i=new Intent(Home.this,FavoritesActivity.class);
                     startActivity(i);

                 }
                else if (id == R.id.nav_logout) {

                     Paper.book().destroy();


                     Intent signIn=new Intent(Home.this,SignIn.class);
                     signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                     startActivity(signIn);


                 }
                 else if (id== R.id.nav_change_password)
                 {
                     showChangePasswordDialog();
                 }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }

    private void showChangePasswordDialog() {

        AlertDialog.Builder alertDialog=new AlertDialog.Builder(Home.this);
        alertDialog.setTitle("CHANGE PASSWORD");
        alertDialog.setMessage("Please fill all information");
        LayoutInflater inflater=LayoutInflater.from(this);
        View layout_pwd=inflater.inflate(R.layout.change_password_layout,null );
        final MaterialEditText edtPassword=(MaterialEditText)layout_pwd.findViewById(R.id.edtPassword);
        final MaterialEditText edtNewPassword=(MaterialEditText)layout_pwd.findViewById(R.id.edtNewPassword);
        final MaterialEditText edtRepeatPassword=(MaterialEditText)layout_pwd.findViewById(R.id.edtRepeatPassword);
        alertDialog.setView(layout_pwd);

        alertDialog.setPositiveButton("CHANGE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                final android.app.AlertDialog waitingDialog=new SpotsDialog(Home.this);
                waitingDialog.show();

                if (edtPassword.getText().toString().equals(Common.currentUser.getPassword()))
                {
                    if (edtNewPassword.getText().toString().equals(edtRepeatPassword.getText().toString()))
                    {
                        Map<String,Object> passwordUpdate=new HashMap<>();
                        passwordUpdate.put("Password",edtNewPassword.getText().toString());

                        DatabaseReference user=FirebaseDatabase.getInstance().getReference("User");
                        user.child(Common.currentUser.getPhone())
                                .updateChildren(passwordUpdate)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                       waitingDialog.dismiss();
                                        Toast.makeText(Home.this, "Password was updated", Toast.LENGTH_SHORT).show();
                                    }
                                })
                               .addOnFailureListener(new OnFailureListener() {
                                   @Override
                                   public void onFailure(@NonNull Exception e) {
                                       Toast.makeText(Home.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                                   }
                               });

                    }
                    else
                    {
                        waitingDialog.dismiss();
                        Toast.makeText(Home.this, "New password doesnt match", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {

                    waitingDialog.dismiss();
                    Toast.makeText(Home.this, "Wrong old password", Toast.LENGTH_SHORT).show();
                }

            }
        });

        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });

        alertDialog.show();




    }
}
