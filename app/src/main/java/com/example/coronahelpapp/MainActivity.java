package com.example.coronahelpapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Location;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.coronahelpapp.Constants.LocationContract.LocationTable;
import com.github.mikephil.charting.charts.BarChart;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


//    https://github.com/fabcira/neverEndingProcessAndroid7- this is the sample sticky service you can use.

public class MainActivity extends AppCompatActivity {
    public String url = "https://corona.lmao.ninja/v2/countries/NGA";

    Button Test, Report, Symptoms, Precautions, News, Movement;
    ImageView profileImage, optionIcon;
    TextView profileName, status, CoronaData, CoronaActive, CoronaRecovered, CoronaCritical;
    BarChart barChart;

    DatabaseReference mDataBase;
    FirebaseUser firebaseUser;
    String uid;
    public String User_Status, dateMarker;

    Location currentLocation;
    LocationRequest locationRequest;
    FusedLocationProviderClient fusedLocationProviderClient;

    Double startLat, startLong, currentLat, currentLong, startLatsp, startLongsp;

    TestDbHelper myDb;
    long date;


    static MainActivity instance;

    public static MainActivity getInstance() {

        return instance;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = this;
        myDb = new TestDbHelper(this);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDataBase = FirebaseDatabase.getInstance().getReference().child("Users");
        uid = firebaseUser.getUid();

        date = System.currentTimeMillis();

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
        optionIcon = findViewById(R.id.option);

        barChart = findViewById(R.id.bar_chart);

        profileName = findViewById(R.id.profile_name);
        status = findViewById(R.id.health_status);

        Test = findViewById(R.id.button_test);
        Report = findViewById(R.id.button_report);
        Symptoms = findViewById(R.id.button_symptoms);
        Precautions = findViewById(R.id.button_precautions);
        News = findViewById(R.id.button_news);
        Movement = findViewById(R.id.button_movement);


        MovementHistory(); // TODO: call through switch button


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

        optionIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(MainActivity.this, optionIcon);
                popup.getMenuInflater().inflate(R.menu.option_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.about:
                                Toast.makeText(MainActivity.this, "create about activity ", Toast.LENGTH_SHORT).show();

                                break;

                            case R.id.developers:
                                Toast.makeText(MainActivity.this, "create developer activity ", Toast.LENGTH_SHORT).show();

                                break;

                        }


                        return true;
                    }
                });

                popup.show();
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
                            CoronaActive.setText("Active: " + jsonObject.getString("active"));
                            CoronaCritical.setText("Deaths:  " + jsonObject.getString("deaths"));
                            CoronaRecovered.setText("Recovered  : " + jsonObject.getString("recovered"));


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

                // TODO: get these values through shared preferences from registration activity.
                startLat = (double) dataSnapshot.child(uid).child("user_location").child("latitude").getValue();
                startLong = (double) dataSnapshot.child(uid).child("user_location").child("longitude").getValue();


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

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                        updateLocation();

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                        Toast.makeText(MainActivity.this, "You must allow location permission for app to work properly",
                                Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();


//            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
//            alertDialog.setTitle("Home location is empty");
//            alertDialog.setMessage("Please click on OK to set your current location as Home. ");
//            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//
//                    Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
//                }
//            });
//            alertDialog.show();


    }

    private void updateLocation() {

        //TODO: check for gps on
        buildLocationRequest();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, getPendingIntent());
    }

    private PendingIntent getPendingIntent() {

        Intent intent = new Intent(this, MyLocationService.class);
        intent.setAction(MyLocationService.ACTION_PROCESS_UPDATE);
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void buildLocationRequest() {

        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
    }

    public void showLocationUpdate(final Location loc) {

        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                currentLocation = loc;

                currentLat = currentLocation.getLatitude();
                currentLong = currentLocation.getLongitude();

                calculateDistance();
            }
        });
    }

    public void calculateDistance() {

        String startlat = String.valueOf(startLat);
        String startlong = String.valueOf(startLong);

        SharedPreferences result = getSharedPreferences("userHome", Context.MODE_PRIVATE);

        startLatsp = Double.parseDouble(result.getString("Latitude", startlat));
        startLongsp = Double.parseDouble(result.getString("longitude", startlong));

        Location startPoint = new Location("userHomeLocation");
        startPoint.setLatitude(startLatsp);
        startPoint.setLongitude(startLongsp);

        Location endPoint = new Location(currentLocation);
        endPoint.setLatitude(currentLat);
        endPoint.setLongitude(currentLong);

        double distance = startPoint.distanceTo(endPoint);

        int distanceRd = (int) Math.round(distance);


        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd MMM");
        dateMarker = formatter.format(date);


        if (distanceRd >= 10) {

            myDb.insertLocation(currentLat, currentLong, dateMarker);
            myDb.close();
            Notify();

            ExportLocation(); //TODO: reactivate

        }


    }

    private void Notify() {

        int nortificationId = 0;

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(2)
                .setContentTitle("Safety Reminder")
                .setContentText("Please remember to wash or sanitize your hands regularly.")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        Uri path = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES .0){
//
//        }


    }

    //TODO: Call this method every 2weeks
    private void ExportLocation() {
        Cursor data = myDb.locationData();
        JSONArray LocationJson = new JSONArray();
        int numRows = data.getCount();

        if (numRows == 0) {

            Toast.makeText(MainActivity.this, "Location Table empty", Toast.LENGTH_SHORT).show();
        } else {

            int i = 0;
            while (data.moveToNext() && !data.isAfterLast()) {

                Double latitude = data.getDouble(data.getColumnIndex(LocationTable.COLUMN_LATITUDE));
                Double longitude = data.getDouble(data.getColumnIndex(LocationTable.COLUMN_LONGITUDE));
                String marker = data.getString(data.getColumnIndex(LocationTable.COLUMN_MARKER_TITLE));

                try {
                    JSONObject object = new JSONObject();
                    object.put("id", i + 1);
                    object.put("latitude", latitude);
                    object.put("longitude", longitude);
                    object.put("marker", marker);
                    LocationJson.put(object);

                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(MainActivity.this, "J-object fail", Toast.LENGTH_SHORT).show();
                }


                i++;
            }

            //TODO: Save 'LocationJson' to firebase
            Log.i("Location: ", LocationJson.toString());
        }

    }

}
