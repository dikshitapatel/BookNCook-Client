package com.example.atul_.eatit;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atul_.eatit.Common.Common;
import com.example.atul_.eatit.Database.Database;
import com.example.atul_.eatit.ViewHolder.CartAdapter;
import com.example.atul_.eatit.model.Order;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.atul_.eatit.model.Request;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.hoang8f.widget.FButton;

public class Cart extends AppCompatActivity {
    
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    
    FirebaseDatabase database;
    DatabaseReference requests;
    TextView txtTotalPrice;
    FButton btnPlace;

    List<Order> cart = new ArrayList<>();

    CartAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_cart);
        
        database=FirebaseDatabase.getInstance();
        requests=database.getReference("Requests");
        
        recyclerView=(RecyclerView)findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        
        txtTotalPrice = (TextView)findViewById(R.id.total);
        btnPlace = (FButton)findViewById(R.id.btnPlaceOrder);
        
        loadListFood();
        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }

            private void showAlertDialog() {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
                alertDialog.setTitle("One more step");
                alertDialog.setMessage("Enter your address:");
                final EditText edtAddresss = new EditText(Cart.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT

                );
                edtAddresss.setLayoutParams(lp);
                alertDialog.setView(edtAddresss);
                alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);
                AlertDialog.Builder yes = alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Request request = new Request(
                                Common.currentUser.getPhone(),
                                Common.currentUser.getName(),
                                edtAddresss.getText().toString(),
                                txtTotalPrice.getText().toString(),
                                cart
                        );

                        requests.child(String.valueOf(System.currentTimeMillis()))
                                .setValue(request);

                        new Database(getBaseContext()).cleanCart();
                        Toast.makeText(Cart.this, "Thank you ,Order placed ", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }

                });
                alertDialog.show();
            }



        });

        
    }


    private void loadListFood() {
        cart = new Database(this).getCarts();
        adapter = new CartAdapter(cart,this);
        recyclerView.setAdapter(adapter);
        int total = 0;
        for(Order order:cart)
            total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
        Locale locale = new Locale("hi","IN");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        txtTotalPrice.setText(fmt.format(total));


    }

}
