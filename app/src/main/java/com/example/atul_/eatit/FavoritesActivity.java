package com.example.atul_.eatit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.atul_.eatit.Database.Database;
import com.example.atul_.eatit.ViewHolder.FavoritesAdapter;
import com.example.atul_.eatit.model.Favorites;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;


    List<Favorites> fav = new ArrayList<>();

    FavoritesAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_favorites);

        database=FirebaseDatabase.getInstance();
        requests=database.getReference("Requests");

        recyclerView=(RecyclerView)findViewById(R.id.recycler_fav);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadListFav();


    }

    private void loadListFav() {
        fav = new Database(this).getFav();
        adapter = new FavoritesAdapter(this,fav);
        recyclerView.setAdapter(adapter);



    }

}
