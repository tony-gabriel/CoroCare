package com.example.coronahelpapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    public String url = "https://corona.lmao.ninja/v2/countries/NGA";
    Button Test, Report, Symptoms, Precautions, News, Movement;
    ImageView profileImage;
    TextView profileName, status, CoronaData, CoronaActive, CoronaRecovered, CoronaCritical;
    DatabaseReference mDataBase;
    FirebaseUser firebaseUser;
    String uid;
    public String User_Status;

    LocationManager locationManager;
    LocationListener locationListener;
    Location userLocation;

    private final long Min_Time = 10000;
    private final long Min_Dist = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDataBase = FirebaseDatabase.getInstance().getReference().child("Users");
        uid = firebaseUser.getUid();

        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }


        CoronaData = findViewById(R.id.CoronaData);
        CoronaActive = findViewById(R.id.CoronaActive);
        CoronaRecovered = findViewById(R.id.CoronaRecovered);
        CoronaCritical = findViewById(R.id.CoronaCritical);
        profileImage = findViewById(R.id.profile_image);

        profileName = findViewById(R.id.profile_name);
        status = findViewById(R.id.health_status);

        Test = findViewById(R.id.button_test);
        Report = findViewById(R.id.button_report);
        Symptoms = findViewById(R.id.button_symptoms);
        Precautions = findViewById(R.id.button_precautions);
        News = findViewById(R.id.button_news);
        Movement = findViewById(R.id.button_movement);

        MovementHistory();


        Test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SelfTest.class));
            }
        });

        Report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Report.class));
            }
        });

        Symptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Symptoms.class));

            }
        });

        Precautions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Precaution.class));

            }
        });

        News.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, News.class));

            }
        });

        Movement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LocationHistory.class));

            }
        });


    }

    void run() throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.clone();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                final String myResponse = response.body().string();
//                Toast.makeText(MainActivity.this, "successful get", Toast.LENGTH_LONG).show();

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(myResponse);
                            CoronaData.setText("Total Cases: " + jsonObject.getString("cases"));
                            CoronaActive.setText("Active Cases: " + jsonObject.getString("active"));
                            CoronaCritical.setText("Total Deaths:  " + jsonObject.getString("deaths"));
                            CoronaRecovered.setText(" Total Recovered  : " + jsonObject.getString("recovered"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }


        });
        mDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String User_name = dataSnapshot.child(uid).child("User_Name").getValue(String.class);
                String User_Status = dataSnapshot.child(uid).child("Health_Status").getValue(String.class);
                String User_pics = dataSnapshot.child(uid).child("ImageUri").getValue(String.class);

                profileName.setText("Hello " + User_name);
                status.setText(User_Status);
                Picasso.get().load(User_pics).error(R.drawable.bg).into(profileImage, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void MovementHistory() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    PackageManager.PERMISSION_GRANTED);
        }


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        try {

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, Min_Time, Min_Dist, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, Min_Time, Min_Dist, locationListener);

        } catch (Exception e) {

            e.printStackTrace();
        }


        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                userLocation = location;

                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                // TODO: Get the Latitude and Longitude and send to database

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };


    }
}
