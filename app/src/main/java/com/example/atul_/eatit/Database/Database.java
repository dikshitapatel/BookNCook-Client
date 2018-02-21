package com.example.atul_.eatit.Database;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.atul_.eatit.model.Order;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by atul_ on 31/01/2018.
 */




public class Database extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "OrderDetail.db";

    public static final String TABLE_NAME = "order1";
    public static final String ProductID = "pro_id";
    public static final String ProductNAME = "name";
    public static final String Price = "price";
    public static final String Quantity = "quantity";
    public static final String Discount = "discount";
    //Create Table Query
    private static final String SQL_CREATE_ORDERS =
            "CREATE TABLE " +TABLE_NAME +" (" + ProductID + "  INTEGER PRIMARY KEY, "
                    + ProductNAME + " TEXT, " + Quantity + "  INTEGER, "
                    + Price + "  INTEGER, " + Discount + "  INTEGER );";

    private static final String SQL_DELETE_ORDERS =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    public Database(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ORDERS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        this.onUpgrade(db, oldVersion, newVersion);

    }



    public List<Order>getCarts()
    {
            SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect={"ProductID,ProductNAME,Quantity,Price,Discount"};
        String sqlTable=TABLE_NAME;

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




    public void addToCart(Order order1) {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO " +TABLE_NAME+ "(ProductID,ProductNAME,Quantity,Price,Discount) VALUES('%s','%s','%s','%s','%s');",


                order1.getProductID(),
                order1.getProductNAME(),
                order1.getQuantity(),
                order1.getPrice(),
                order1.getDiscount());
        db.execSQL(query);

    }

    public void cleanCart() {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM " + TABLE_NAME + ";");


        db.execSQL(query);

    }






}
