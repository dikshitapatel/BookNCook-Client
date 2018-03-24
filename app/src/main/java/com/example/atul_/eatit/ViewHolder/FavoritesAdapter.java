package com.example.atul_.eatit.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.atul_.eatit.FavoritesActivity;
import com.example.atul_.eatit.R;
import com.example.atul_.eatit.model.Favorites;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Admin on 09-Mar-18.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesViewHolder> {

    Context context;
    private List<Favorites> favoritesList;
    TextView f,fname,fprice;


    public FavoritesAdapter(FavoritesActivity favoritesActivity, List<Favorites> allFavorites) {

        this.favoritesList = allFavorites;
        this.context=favoritesActivity;

    }

    @Override
    public FavoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context)
                .inflate(R.layout.favorites_item,parent,false);



         f=(TextView)itemView.findViewById(R.id.food_id);
        fname=(TextView)itemView.findViewById(R.id.food_name);
        fprice=(TextView)itemView.findViewById(R.id.food_price);
        return new FavoritesViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(FavoritesViewHolder viewHolder, int position) {


        Picasso.with(context).load(favoritesList.get(position).getFoodId());
        Picasso.with(context).load(favoritesList.get(position).getFoodName());
        Picasso.with(context).load(favoritesList.get(position).getFoodPrice());

f.setText(favoritesList.get(position).getFoodId());
        fname.setText(favoritesList.get(position).getFoodName());

        fprice.setText(favoritesList.get(position).getFoodPrice());

    }

    @Override
    public int getItemCount() {
        return favoritesList.size();
    }


    public void removeItem(int position)
    {
        favoritesList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Favorites item, int position)
    {
        favoritesList.add(position,item);
        notifyItemInserted(position);
    }
}

