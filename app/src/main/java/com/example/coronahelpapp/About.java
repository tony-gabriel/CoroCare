package com.example.coronahelpapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ImageView image = findViewById(R.id.logo);
        Picasso.get()
                .load(R.drawable.company_logo)
                .fit()
                .into(image);
    }

    public void backAbout(View view) {
        finish();
    }
}
