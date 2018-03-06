package com.example.atul_.eatit.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteException;
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




public class Database extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "EatIt.db";

    public static SQLiteDatabase db;

    public String f1,f2,f3,f4,f5;



    public Database(Context context) {

        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase d) {



        db=d;

        db.execSQL("CREATE TABLE IF NOT EXISTS OrderDetail(ProductId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
                "ProductName TEXT," +
                "Price INTEGER," +
                "Quantity INTEGER," +
                "Discount INTEGER);");

        db.execSQL("CREATE TABLE IF NOT EXISTS Favorites(FoodId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE);");



    }



    public List<Order>getCarts()
    {


        try{
            db=SQLiteDatabase.openDatabase("EatIt.db",null,Context.MODE_PRIVATE);
            // String path ="D:\EatIt.db" + DATABASE_NAME;
            //  SQLiteDatabase checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
            //System.out.println(checkDB);
        } catch(SQLiteException e){
            System.out.println("Exception");
        }


        //SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect={"ProductId,ProductName,Quantity,Price,Discount"};
        String sqlTable="OrderDetail";



        qb.setTables(sqlTable);


        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);

        final List<Order>result = new ArrayList<>();
        if(c.moveToFirst())
        {
            do{
                result.add(new Order(c.getString(c.getColumnIndex("ProductId")),
                        c.getString(c.getColumnIndex("ProductName")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("Price")),
                        c.getString(c.getColumnIndex("Discount"))
                ));

            }while(c.moveToNext());
        }
        return result;
    }




    public void insert(String foodId, String name, String number, String price, String discount) {


        //String query = String.format("INSERT INTO OrderDetail(ProductId,ProductName,Quantity,Price,Discount) VALUES('%s','%s','%s','%s','%s');",
        //Order order1 = null;

        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();

        values.put("ProductId",foodId);
        values.put("ProductName",name);
        values.put("Quantity",number);
        values.put("Price",price);
        values.put("Discount",discount);

        db.insert("OrderDetail", null, values);

    }

    public void cleanCart() {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail");


        db.execSQL(query);

    }

    public void addToFavorites(String foodId)
    {
        SQLiteDatabase db=getReadableDatabase();
        String query=String.format("INSERT INTO Favorites(FoodId) VALUES(%s);",foodId);
        db.execSQL(query);
    }

    public void removeFromFavorites(String foodId)
    {
        SQLiteDatabase db=getReadableDatabase();
        String query=String.format("DELETE FROM Favorites WHERE FoodId='%s';",foodId);
        db.execSQL(query);
    }

    public void getAData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query=String.format("SELECT * FROM OrderDetail");
        Cursor cursor=db.rawQuery(query,null);
        if (cursor.getCount()<=0)
        {
            cursor.close();

        }
        cursor.close();

    }

   public boolean isFavorites(String foodId)
    {
        SQLiteDatabase db=getReadableDatabase();
        String query=String.format("SELECT * FROM Favorites WHERE FoodId='%s';",foodId);
        Cursor cursor=db.rawQuery(query,null);
        if (cursor.getCount()<=0)
        {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }






}
