package com.example.atul_.eatit.model;

/**
 * Created by Karan Patel on 17-01-2018.
 */

public class Food {
    private String Name,Image, Description,Price,Discount,MenuId;

    public Food(){

    }


    public Food(String name,String image,String descripton,String price,String discount,String menuId) {
        Name = name;
        Image = image;
        Description = descripton;
        Price = price;
        Discount = discount;
        MenuId = menuId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescripton() {
        return Description;
    }

    public void setDescription(String descripton) {
        Description = descripton;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }
}



