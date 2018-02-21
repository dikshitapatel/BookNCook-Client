package com.example.atul_.eatit.model;

/**
 * Created by atul_ on 31/01/2018.
 */

public class Order {
    private String ProductID;
    private String ProductNAME;
    private String Quantity;
    private String Price;
    private String Discount;



    public Order() {


    }

    public Order(String productId, String productName, String quantity, String price, String discount) {

        ProductID = productId;
        ProductNAME = productName;
        Quantity = quantity;
        Price = price;
        Discount = discount;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productId) {
        ProductID = productId;
    }

    public String getProductNAME() {
        return ProductNAME;
    }

    public void setProductNAME(String productName) {
        ProductNAME = productName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
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
}
