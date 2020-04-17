package com.example.coronahelpapp;

public class User {
    public String  User_Name, User_phone, Health_Status, ImageUri;

    public User(){

    }

    public User( String username, String phone, String health_Status, String ImageUri) {

        this.User_Name =username;
        this.User_phone = phone;
        this.Health_Status=health_Status;
        this.ImageUri=ImageUri;
    }


}