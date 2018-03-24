package com.example.atul_.eatit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.atul_.eatit.Common.Common;
import com.example.atul_.eatit.Database.Database;
import com.example.atul_.eatit.Interface.ItemClickListener;
import com.example.atul_.eatit.ViewHolder.FavoritesAdapter;
import com.example.atul_.eatit.ViewHolder.FoodViewHolder;
import com.example.atul_.eatit.model.Favorites;
import com.example.atul_.eatit.model.Food;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodList;
    String categoryId="";
    FirebaseRecyclerAdapter<Food,FoodViewHolder> adapter;
    Database localDB;


    FirebaseRecyclerAdapter<Food,FoodViewHolder> searchadapter;
    List<String> suggestList=new ArrayList<>();
    MaterialSearchBar materialSearchBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        database=FirebaseDatabase.getInstance();
        foodList=database.getReference("Food");

        localDB=new Database(this);

        recyclerView=(RecyclerView)findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if(getIntent()!= null)
            categoryId=getIntent().getStringExtra("CategoryId");
        if(!categoryId.isEmpty() && categoryId!= null)
        {

            if(Common.isConnectedToInternet(getBaseContext()))
                loadListFood(categoryId);

            else {
                Toast.makeText(FoodList.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                return;
            }


        }

        materialSearchBar=(MaterialSearchBar)findViewById(R.id.searchBar);
        materialSearchBar.setHint("Search Your Food");

        loadsuggest();
        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> suggest=new ArrayList<String>();
                for(String search:suggestList){
                    if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                materialSearchBar.setLastSuggestions(suggest);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

                if(!enabled)
                    recyclerView.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text);

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
    }

    private void loadsuggest() {
        foodList.orderByChild("MenuId").equalTo(categoryId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Food item=postSnapshot.getValue(Food.class);
                    suggestList.add(item.getName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void startSearch(CharSequence text) {
        searchadapter=new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,R.layout.food_item,FoodViewHolder.class,
                foodList.orderByChild("name").equalTo(text.toString())) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                viewHolder.food_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.food_image);

                final Food local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Intent foodDetail = new Intent(FoodList.this,FoodDetail.class);
                        foodDetail.putExtra("FoodId",searchadapter.getRef(position).getKey());
                        startActivity(foodDetail);
                    }
                });
            }
        };
        recyclerView.setAdapter(searchadapter);
    }



    private void loadListFood(String categoryId) {
        adapter=new FirebaseRecyclerAdapter<Food,FoodViewHolder> (Food.class,R.layout.food_item,FoodViewHolder.class,
                foodList.orderByChild("MenuId").equalTo(categoryId)
        ){
            protected void populateViewHolder(final FoodViewHolder viewHolder, final Food model, final int position){
                viewHolder.food_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.food_image);

                if (localDB.isFavorites(adapter.getRef(position).getKey()))
                    viewHolder.fav_image.setImageResource(R.drawable.ic_favorite_black_24dp);

                viewHolder.fav_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Favorites favorites=new Favorites();
                        //  localDB.addToFavorites(adapter.getRef(position).getKey());
                        favorites.setFoodId(adapter.getRef(position).getKey());
                        favorites.setFoodName(model.getName());
                        favorites.setFoodPrice(model.getPrice());
                        if (!localDB.isFavorites(adapter.getRef(position).getKey()))
                        {

                            localDB.addToFavorites(favorites);
                            viewHolder.fav_image.setImageResource(R.drawable.ic_favorite_black_24dp);
                            Toast.makeText(FoodList.this,""+model.getName()+"was added to favorites",Toast.LENGTH_SHORT).show();
                            localDB.takeFav();

                        }
                        else
                        {

                            localDB.removeFromFavorites(adapter.getRef(position).getKey());
                            viewHolder.fav_image.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                            Toast.makeText(FoodList.this,""+model.getName()+"was removed from favorites",Toast.LENGTH_SHORT).show();

                        }

                    }
                });



                final Food local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Intent foodDetail = new Intent(FoodList.this,FoodDetail.class);
                        foodDetail.putExtra("FoodId",adapter.getRef(position).getKey());
                        startActivity(foodDetail);
                    }
                });

            }
        };



        recyclerView.setAdapter(adapter);

    }
}

