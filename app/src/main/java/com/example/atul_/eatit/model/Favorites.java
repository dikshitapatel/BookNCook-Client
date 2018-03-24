package com.example.atul_.eatit.model;

/**
 * Created by Admin on 16-Mar-18.
 */

public class Favorites {

    private String FoodId;
    private  String FoodName;
    private String FoodPrice;


    public Favorites()
    {

    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public String getFoodPrice() {
        return FoodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        FoodPrice = foodPrice;
    }



    public Favorites(String foodId, String foodName, String foodPrice)
    {
        FoodId=foodId;
        FoodName=foodName;
        FoodPrice=foodPrice;



    }

    public String getFoodId() {
        return FoodId;
    }

    public void setFoodId(String foodId) {
        FoodId = foodId;
    }


}
