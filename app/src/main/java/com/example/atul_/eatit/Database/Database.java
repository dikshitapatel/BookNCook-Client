package com.example.atul_.eatit.Database;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.atul_.eatit.model.Order;
import com.google.firebase.database.ValueEventListener;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;


import java.util.ArrayList;
import java.util.List;

import static android.R.attr.name;

/**
 * Created by atul_ on 31/01/2018.
 */




public class Database extends SQLiteAssetHelper {


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "EatIt.db";

    public Database(Context context) {

        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    public List<Order>getCarts()
    {
            SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect={"ProductNAME","ProductID","Quantity","Price","Discount"};
        String sqlTable="OrderDetail";

        qb.setTables(sqlTable);

        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);
        final List<Order>result = new ArrayList<>();
        if(c.moveToFirst())
        {
            do{
                result.add(new Order(c.getString(c.getColumnIndex("ProductID")),
                c.getString(c.getColumnIndex("ProductNAME")),
                c.getString(c.getColumnIndex("Quantity")),
                c.getString(c.getColumnIndex("Price")),
                c.getString(c.getColumnIndex("Discount"))
                        ));

            }while(c.moveToNext());
        }
        return result;
    }




    public void addToCart(Order order) {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO OrderDetail(ProductID,ProductNAME,Quantity,Price,Discount) VALUES('%s','%s','%s','%s','%s');",
                order.getProductID(),
                order.getProductNAME(),
                order.getQuantity(),
                order.getPrice(),
                order.getDiscount());
        db.execSQL(query);

    }

    public void cleanCart() {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail");


        db.execSQL(query);

    }






}
