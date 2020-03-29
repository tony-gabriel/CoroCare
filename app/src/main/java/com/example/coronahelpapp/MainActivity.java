package com.example.coronahelpapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button Test, Report, Symptoms, Precautions, News, Movement;
    ImageView profileImage;
    TextView profileName, status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileImage = findViewById(R.id.profile_image);

        profileName = findViewById(R.id.profile_name);
        status = findViewById(R.id.health_status);

        Test = findViewById(R.id.button_test);
        Report = findViewById(R.id.button_report);
        Symptoms = findViewById(R.id.button_symptoms);
        Precautions = findViewById(R.id.button_precautions);
        News = findViewById(R.id.button_news);
        Movement = findViewById(R.id.button_movement);

        Test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SelfTest.class));
            }
        });

        Report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        Symptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Precautions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        News.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Movement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LocationHistory.class));

            }
        });

    }
}
