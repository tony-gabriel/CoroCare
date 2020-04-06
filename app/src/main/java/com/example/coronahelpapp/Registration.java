package com.example.coronahelpapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {

    EditText fullname;
    EditText mobile;
    Button proceed;
    RadioButton rbtMale;
    RadioButton rbtFemale;
    RadioButton rbtHome;
    RadioButton rbtAway;
    DatabaseReference dbreference;
    UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        fullname = findViewById(R.id.fullname);
        mobile = findViewById(R.id.mobile);
        proceed = findViewById(R.id.proceed);
        userData = new UserData();
        dbreference = FirebaseDatabase.getInstance().getReference().child("UserData");


        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = fullname.getText().toString();
                String mobileNumber = mobile.getText().toString();


                if (username.isEmpty()) {
                    fullname.requestFocus();
                    fullname.setError("please enter a value");
                    return;

                }

                if (mobileNumber.isEmpty()) {
                    mobile.requestFocus();
                    mobile.setError("please enter a value");
                    return;

                }

                userData.setFullname(username);
                userData.setPhone(mobileNumber);
                userData.setHealth("healthy");
                dbreference.push().setValue(userData);


                Intent intent = new Intent(Registration.this, ProfileImageReg.class);
                startActivity(intent);
                finish();

            }
        });


    }


}

