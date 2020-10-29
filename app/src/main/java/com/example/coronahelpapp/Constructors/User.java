package com.example.coronahelpapp.Constructors;

import com.google.android.gms.maps.model.LatLng;

public class User {

    public String User_Name, User_phone, ImageUri, User_Age, User_gender;
    public LatLng user_location;

    public User() {
    }

    public User(String username, String phone, String userAge, String gender, String ImageUri, LatLng userLocation) {

        this.User_Name = username;
        this.User_phone = phone;
        this.User_Age = userAge;
        this.User_gender = gender;
        this.ImageUri = ImageUri;
        this.user_location = userLocation;
    }
}
