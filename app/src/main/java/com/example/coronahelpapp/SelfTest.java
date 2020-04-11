package com.example.coronahelpapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SelfTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_test);
    }

    public void back(View view) {
        Intent intent=new Intent (this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
