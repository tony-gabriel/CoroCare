package com.example.coronahelpapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Registration extends AppCompatActivity {

    FirebaseUser firebaseUser;


    EditText fullName;
    EditText mobile, age;
    Button proceed;
    RadioGroup radioGroup1, radioGroup2;
    DatabaseReference mDatabase;
    String health_Status = "healthy";
    String ImageUri = "";

    public String userId, Gender;

    LatLng userLocation;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();

        fullName = findViewById(R.id.fullname);
        mobile = findViewById(R.id.mobile);
        age = findViewById(R.id.age);

        radioGroup1 = findViewById(R.id.radio_group_gender);
        radioGroup2 = findViewById(R.id.radio_group_location);

        proceed = findViewById(R.id.proceed);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");


        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {

                    case R.id.radioButtonHome:
                        permissionCheck();
                        GetLocation();
                        break;

                }

            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void
            onClick(View view) {

                RadioButton rbSelected = findViewById(radioGroup1.getCheckedRadioButtonId());
                int selectedNr = radioGroup1.indexOfChild(rbSelected);

                if (selectedNr == 1) {

                    Gender = "Female";

                } else {

                    Gender = "Male";
                }

                final String username = fullName.getText().toString();
                final String mobileNumber = mobile.getText().toString();
                final String userAge = age.getText().toString();
                String health = "healthy";


                if (username.isEmpty()) {
                    fullName.requestFocus();
                    fullName.setError("please enter your full name");
                    return;

                }

                if (mobileNumber.length() < 11 || mobileNumber.length() > 14) {
                    mobile.requestFocus();
                    mobile.setError("please enter a valid phone number");
                    return;

                }

                if (userAge.isEmpty() || userAge.length() > 3) {
                    age.requestFocus();
                    age.setError("please enter a valid age range");
                    return;

                }

                final ProgressDialog progressDialog = new ProgressDialog(Registration.this);
                progressDialog.setTitle("Loading...");
                progressDialog.show();


                mAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {


                            User user = new User(username, mobileNumber, userAge, Gender, health_Status, ImageUri, userLocation);

                            FirebaseDatabase.getInstance().getReference("Users").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance()
                                    .getCurrentUser())).getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(Task<Void> task) {


                                    if (task.isSuccessful()) {


                                        progressDialog.dismiss();
                                        Gotoprofile();


                                    } else {

                                        progressDialog.dismiss();

                                        Toast.makeText(Registration.this, "Registration Failed", Toast.LENGTH_LONG).show();


                                    }

                                }


                            });


                        }


                    }

                });

            }

            private void Gotoprofile() {

                Intent intent = new Intent(getApplicationContext(), ProfileImageReg.class);
                startActivity(intent);
                finish();


            }


        });
    }

    private void GetLocation() {

        final LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(100);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        LocationServices.getFusedLocationProviderClient(this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {

                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);

                        LocationServices.getFusedLocationProviderClient(Registration.this)
                                .removeLocationUpdates(this);

                        if (locationResult != null && locationResult.getLocations().size() > 0) {

                            int latestLocationIndex = locationResult.getLocations().size() - 1;

                            userLocation = new LatLng(locationResult.getLocations().get(latestLocationIndex).getLatitude(),
                                    locationResult.getLocations().get(latestLocationIndex).getLongitude());

                            if (userLocation != null) {

                                String mLat = String.valueOf(locationResult.getLocations().get(latestLocationIndex).getLatitude());
                                String mLong = String.valueOf(locationResult.getLocations().get(latestLocationIndex).getLongitude());

                                if (!mLat.isEmpty() && !mLong.isEmpty()) {

                                    SharedPreferences prefs = getSharedPreferences("userHome", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putString("Latitude", mLat);
                                    editor.putString("Longitude", mLong);
                                    editor.apply();
                                } else {
                                    Toast.makeText(Registration.this, "shared preferences not saved", Toast.LENGTH_SHORT).show();
                                }

                                Toast.makeText(Registration.this, "Location confirmed", Toast.LENGTH_SHORT).show();
                            } else {

                                Toast.makeText(Registration.this, "Location not confirmed", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }, Looper.getMainLooper());


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            GetLocation();
        }
    }

    private void permissionCheck() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    PackageManager.PERMISSION_GRANTED);
        }
    }

}