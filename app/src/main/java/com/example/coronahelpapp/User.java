package com.example.coronahelpapp;

import com.google.android.gms.maps.model.LatLng;

public class User {

    public String User_Name, User_phone, Health_Status, ImageUri, User_Age, User_gender;
    public LatLng user_location;

    public User() {
    }

    public User(String username, String phone, String userAge, String gender, String health_Status, String ImageUri, LatLng location) {

        this.User_Name = username;
        this.User_phone = phone;
        this.User_Age = userAge;
        this.User_gender = gender;
        this.Health_Status = health_Status;
        this.ImageUri = ImageUri;
        this.user_location = location;
    }
}
