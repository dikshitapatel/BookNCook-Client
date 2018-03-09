package com.example.atul_.eatit.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import com.example.atul_.eatit.Interface.ItemClickListener;
import com.example.atul_.eatit.R;

/**
 * Created by Admin on 06-Mar-18.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener {

    public TextView txtOrderId;
    public TextView txtOrderPhone;
    public TextView txtOrderAddress;
    public TextView txtOrderStatus;
    private ItemClickListener itemClickListener;

    public OrderViewHolder(View itemView) {
        super(itemView);

        txtOrderId = (TextView)itemView.findViewById(R.id.order_Name);
        txtOrderStatus = (TextView)itemView.findViewById(R.id.order_Status);
        txtOrderPhone = (TextView)itemView.findViewById(R.id.order_Phone);
        txtOrderAddress = (TextView)itemView.findViewById(R.id.order_Address );

        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;

    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select the Action");

        menu.add(0, 0, getAdapterPosition(), "Update");
        menu.add(0, 1, getAdapterPosition(), "Delete");

    }
}

