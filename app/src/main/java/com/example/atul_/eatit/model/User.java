package com.example.atul_.eatit.model;

/**
 * Created by atul_ on 11/01/2018.
 */

public class User {
    private String Name;
    private  String Password;
    private String Phone;
    private String secureCode;
    private String IsStaff;





    public User(){

    }

    public User(String name, String password,String secureCode) {

        Name = name;
        Password = password;
        IsStaff="false";
        this.secureCode=secureCode;

    }
    public String getSecureCode() {
        return secureCode;
    }

    public void setSecureCode(String secureCode) {
        this.secureCode = secureCode;
    }

    public String getIsStaff() {
        return IsStaff;
    }
    public void setIsStaff(String isStaff) {
        IsStaff=isStaff;
    }



    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}


