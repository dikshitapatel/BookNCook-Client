package com.example.atul_.eatit.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.atul_.eatit.Interface.ItemClickListener;
import com.example.atul_.eatit.R;

/**
 * Created by Admin on 09-Mar-18.
 */

public class FavoritesViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView food_id,food_name,food_price;

    public Object quick_cart;

    public void setItemClickListener(ItemClickListener itemClickListener) {

        this.itemClickListener = itemClickListener;
    }

    public RelativeLayout view_background;
    public LinearLayout view_foreground;

    private ItemClickListener itemClickListener;
    public FavoritesViewHolder(View itemView) {
        super(itemView);

        food_id= (TextView)itemView.findViewById(R.id.food_id);
        food_name= (TextView)itemView.findViewById(R.id.food_name);
        food_price= (TextView)itemView.findViewById(R.id.food_price);

        view_background=(RelativeLayout)itemView.findViewById(R.id.view_background);
        view_foreground=(LinearLayout)itemView.findViewById(R.id.view_foreground);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(),false);

    }
}
