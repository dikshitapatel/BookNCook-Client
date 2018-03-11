package com.example.atul_.eatit.model;

/**
 * Created by Karan Patel on 11-03-2018.
 */

public class Rating {
    private String userPhone;
    private String rateValue;
    private String foodId;
    private String comment;

    public Rating() {
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getRateValue() {
        return rateValue;
    }

    public void setRateValue(String rateValue) {
        this.rateValue = rateValue;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Rating(String userPhone, String rateValue, String foodId, String comment) {
        this.userPhone = userPhone;
        this.rateValue = rateValue;
        this.foodId = foodId;
        this.comment = comment;
    }


}








