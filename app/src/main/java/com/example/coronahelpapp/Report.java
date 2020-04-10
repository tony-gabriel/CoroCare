package com.example.coronahelpapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Report extends AppCompatActivity {

        final String [] states = {"<---Select your state--->", "NCDC (National", "Akwa Ibom", "Anambra", "Bayelsa",
            "Bauchi", "Cross River", "Delta", "Enugu", "Ekiti", "Edo", "FCT", "Imo", "Kano", "Kaduna",
            "Lagos", "Niger", "Osun", "Ogun", "Ondo", "Oyo", "Plateau", "Rivers", "Sokoto", "Yobe", "Zamfara"};

    final String [] ncdc = {"08009700010"};
    final String [] akwa = {"08028442194", "08037934966", "09023330092"};
    final String [] anambra = {"08145434416"};
    final String [] bayelsa = {"08039216821", "07019304970", "08151693570"};
    final String [] bauchi = {"08023909309", "08032717887", "08059600898", "08080330216", "08036911698"};
    final String [] cross = {"09036281412"};
    final String [] delta = {"08033521960", "08035078541", "08030758179", "09065031241"};
    final String [] enugu= {"117", "112", "08182555550"};
    final String [] ekiti= {"112", "09062970434", "09062970435", "09062970436"};
    final String [] edo= {"08084096723", "08064258163", "08035835529"};
    final String [] fct= {"08099936312", "07080631500"};
    final String [] imo= {"08099555577", "07087110839"};
    final String [] kano= {"09093995333", "09093995444"};
    final String [] kaduna= {"08025088304", "08032401473", "08035871662", "08037808191", "08036045755", "08027396344"};
    final String [] lagos= {"08000267662"};
    final String [] niger= {"08038246018", "09093093642", "08077213070"};
    final String [] osun= {"293", "08035025692", "08033908772", "08056456250"};
    final String [] ogun= {"08188978393", "08188978392"};
    final String [] ondo= {"08002684319", "07002684319"};
    final String [] oyo= {"08095394000"};
    final String [] plateau= {"07032864444", "08035422711", "08065486416", "08035779917"};
    final String [] rivers= {"08056109538", "08031888093", "08033124314"};
    final String [] sokoto= {"07031935037", "08022069567", "08035074228", "08036394462"};
    final String [] yobe= {"08131834764", "07041116027"};
    final String [] zamfara= {"08035626731", "08035161538", "08161330774", "08065408696", "08105009888", "08063075385"};

    FloatingActionButton fab;
    Spinner state, numbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        state = findViewById(R.id.spinner_main);
        numbers = findViewById(R.id.spinner_sub);
        fab = findViewById(R.id.floatingActionButton);


        ArrayAdapter<String> state_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, states);

        state.setAdapter(state_adapter);


        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position){



                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }
}
