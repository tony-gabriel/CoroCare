package com.example.coronahelpapp;

public class UserData {

   public String fullname;
   public  String Location;
    public String health;
    public String phone;
   public String imageUrl;

    public UserData() {

    }


    public UserData(String username, String mobileNumber, String health) {

        this.fullname=username;
        this.phone=mobileNumber;
        this.health=health;
    }

}

