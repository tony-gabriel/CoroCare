package com.example.coronahelpapp;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.example.coronahelpapp.Database.TestDbHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


//    https://github.com/fabcira/neverEndingProcessAndroid7- this is the sample sticky service you can use.

public class MainActivity extends AppCompatActivity {
    public String url = "https://corona.lmao.ninja/v2/countries/NGA";
    String url2 = "https://covid19.ncdc.gov.ng/";

    Button Test, Report, Symptoms, Precautions;
    ImageView profileImage, optionIcon, ImgErr;
    TextView profileName, CoronaData, CoronaActive, CoronaRecovered, CoronaCritical, Samples;
    ConstraintLayout profile;

    DatabaseReference mDataBase;
    FirebaseUser firebaseUser;
    String uid, User_pics;
    public String dateMarker;

    Location currentLocation;
    LocationRequest locationRequest;
    FusedLocationProviderClient fusedLocationProviderClient;

    Double currentLat, currentLong, startLatsp, startLongsp;

    TestDbHelper myDb;
    long date;
    SharedPreferences prefs;


    static MainActivity instance;

    public static MainActivity getInstance() {

        return instance;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.viewColor));

        prefs = getApplicationContext().getSharedPreferences("userHome", MODE_PRIVATE);
        startLatsp = Double.parseDouble(prefs.getString("Latitude", "0.0"));
        startLongsp = Double.parseDouble(prefs.getString("Longitude", "0.0"));


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

//        CheckLocation();


        CoronaData = findViewById(R.id.CoronaData);
        Samples = findViewById(R.id.samples);
        CoronaActive = findViewById(R.id.CoronaActive);
        CoronaRecovered = findViewById(R.id.CoronaRecovered);
        CoronaCritical = findViewById(R.id.CoronaCritical);
        profileImage = findViewById(R.id.profile_image);
        optionIcon = findViewById(R.id.option);
        ImgErr = findViewById(R.id.imgErr);

        profileName = findViewById(R.id.profile_name);
        profile = findViewById(R.id.profile_container);

        Test = findViewById(R.id.button_test);
        Report = findViewById(R.id.button_report);
        Symptoms = findViewById(R.id.button_symptoms);
        Precautions = findViewById(R.id.button_precautions);

        if (!CheckInternet()) {

            ImgErr.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(R.drawable.ic_no_wifi)
                    .into(ImgErr);
        } else {
            ImgErr.setVisibility(View.GONE);
        }


//        MovementHistory();


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
                                startActivity(new Intent(MainActivity.this, About.class));
                                break;

                            case R.id.remove:

                                final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                                alert.setTitle("Delete your Record");
                                alert.setMessage("This action cannot be undone.");
                                alert.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        RemoveUser();
                                    }
                                });
                                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                alert.show();
                                break;

                            case R.id.visit:
                                Visit();
                                break;
                        }
                        return true;
                    }
                });

                popup.show();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (User_pics.equals("")) {
                    startActivity(new Intent(MainActivity.this, ProfileImageReg.class));
                    Toast.makeText(MainActivity.this, "Please complete registration", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(MainActivity.this, ProfileView.class));
                }
            }
        });

    }

    private void Visit() {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(MainActivity.this, R.color.viewColor));
        builder.addDefaultShareMenuItem().setShowTitle(true).build();

        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(MainActivity.this, Uri.parse(url2));
    }

    private void RemoveUser() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Removing your medical record");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(User_pics);
        storageReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                startActivity(new Intent(MainActivity.this, Registration.class));
                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Snackbar snackbar = Snackbar.make(findViewById(R.id.mainVi), e.getMessage(), Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Snackbar snackbar = Snackbar.make(findViewById(R.id.mainVi), e.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

    }

//    private void CheckLocation() {
//        if (startLatsp == 0.0 && startLongsp == 0.0) {
//            GetLocationMsg();
//        }
//    }


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

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(myResponse);
                            CoronaData.setText("Total Cases: " + jsonObject.getString("cases"));
                            CoronaActive.setText(jsonObject.getString("active"));
                            CoronaCritical.setText(jsonObject.getString("deaths"));
                            CoronaRecovered.setText(jsonObject.getString("recovered"));
                            Samples.setText(jsonObject.getString("tests"));


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
                User_pics = dataSnapshot.child(uid).child("ImageUri").getValue(String.class);

                if (!CheckInternet()) {

                    ImgErr.setVisibility(View.VISIBLE);
                    Picasso.get()
                            .load(R.drawable.ic_no_wifi)
                            .into(ImgErr);

                    //ToDo: set name and health status from shared preferences
                }

                ImgErr.setVisibility(View.GONE);

                int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

                if (hour >= 0 && hour < 12) {

                    profileName.setText("Good Morning, " + "\n" + User_name);

                } else if (hour >= 12 && hour < 16) {

                    profileName.setText("Good Afternoon, " + "\n" + User_name);

                } else if (hour >= 16 && hour < 21) {

                    profileName.setText("Good Evening, " + "\n" + User_name);

                } else if (hour >= 21 && hour < 24) {

                    profileName.setText("Good Night, " + "\n" + User_name);
                }

                try {

                    Picasso
                            .get()
                            .load(User_pics)
                            .error(R.drawable.img_profile)
                            .placeholder(R.drawable.img_profile)
                            .into(profileImage);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


//    private void MovementHistory() {
//
//        Dexter.withActivity(this)
//                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
//                .withListener(new PermissionListener() {
//                    @Override
//                    public void onPermissionGranted(PermissionGrantedResponse response) {
//                        updateLocation();
//                    }
//
//                    @Override
//                    public void onPermissionDenied(PermissionDeniedResponse response) {
//                        Toast.makeText(MainActivity.this, "You must allow location permission for app to work properly",
//                                Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
//
//                    }
//                }).check();
//    }

//    private void updateLocation() {
//
//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//
//        if (!gps) {
//            showSettingsAlert();
//        }
//
//        buildLocationRequest();
//
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        fusedLocationProviderClient.requestLocationUpdates(locationRequest, getPendingIntent());
//    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(this, MyLocationService.class);
        intent.setAction(MyLocationService.ACTION_PROCESS_UPDATE);
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

//    private void buildLocationRequest() {
//        locationRequest = new LocationRequest();
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setInterval(1000);
//        locationRequest.setFastestInterval(1000);
//    }

//    public void showLocationUpdate(final Location loc) {
//        MainActivity.this.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                currentLocation = loc;
//                currentLat = currentLocation.getLatitude();
//                currentLong = currentLocation.getLongitude();
//
//                calculateDistance();
//            }
//        });
//    }

//    public void calculateDistance() {
//        if (startLatsp != 0.0 && startLongsp != 0.0) {
//            Location startPoint = new Location("userHomeLocation");
//            startPoint.setLatitude(startLatsp);
//            startPoint.setLongitude(startLongsp);
//
//            Location endPoint = new Location(currentLocation);
//            endPoint.setLatitude(currentLat);
//            endPoint.setLongitude(currentLong);
//
//            double distance = startPoint.distanceTo(endPoint);
//            int distanceRd = (int) Math.round(distance);
//
////            Toast.makeText(this, "You are " + distanceRd + " meters away from home.", Toast.LENGTH_SHORT).show();
//
//            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd MMM");
//            dateMarker = formatter.format(date);
//
//            if (distanceRd >= 1) {
////                SaveLocation(currentLat, currentLong, dateMarker);
//                handler.SaveLocation(currentLat, currentLong, dateMarker);
//            }
//
//        }
//
//
//    }

//    private void GetLocationMsg() {
//        final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
//        alert.setTitle("Home Location not Set");
//        alert.setMessage("Your home location is not set. Do you want to set your current location as your home location?");
//
//        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                GetLocation();
//            }
//        });
//
//        alert.setNegativeButton("No, Later", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//        alert.show();
//    }

//    private void GetLocation() {
//        final String la = String.valueOf(currentLat);
//        final String lo = String.valueOf(currentLong);
//        LatLng location = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
//        if (currentLat != null && currentLong != null) {
//
//            SharedPreferences.Editor edit = prefs.edit();
//            edit.putString("Latitude", la);
//            edit.putString("Longitude", lo);
//            edit.apply();
//
//            mDataBase.child(uid).child("user_location").setValue(location)
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//
//                                Toast.makeText(MainActivity.this, "Home location updated.", Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(MainActivity.this, "Update failed, try again later.", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//        }
//    }

//    public void SaveLocation(Double lat, Double lng, String marker) {
//
//        boolean insert = myDb.insertLocation(lat, lng, marker);
//
//        if (insert) {
//
//            ExportLocation1();
//        }
//        myDb.close();
//    }

//    private void showSettingsAlert() {
//
//        final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
//        alert.setTitle("Turn on GPS");
//        alert.setMessage("GPS is required for app's location services to work. Go to settings menu and enable GPS.");
//
//        alert.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                MainActivity.this.startActivity(intent);
//            }
//        });
//
//        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//        alert.show();
//    }

//    private void ExportLocation1() {
//        Cursor data = myDb.locationData();
//        JSONArray LocationJson = new JSONArray();
//        int numRows = data.getCount();
//
//        if (numRows == 0) {
//            Toast.makeText(MainActivity.this, "Location Table empty", Toast.LENGTH_SHORT).show();
//        } else {
//
//            int i = 0;
//            while (data.moveToNext()) {
//
//                Double latitude = data.getDouble(data.getColumnIndex(LocationTable.COLUMN_LATITUDE));
//                Double longitude = data.getDouble(data.getColumnIndex(LocationTable.COLUMN_LONGITUDE));
//                String marker = data.getString(data.getColumnIndex(LocationTable.COLUMN_MARKER_TITLE));
//
//                try {
//                    JSONObject object = new JSONObject();
//                    object.put("id", i + 1);
//                    object.put("latitude", latitude);
//                    object.put("longitude", longitude);
//                    object.put("marker", marker);
//                    LocationJson.put(object);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//
//                    Toast.makeText(MainActivity.this, "J-object fail", Toast.LENGTH_SHORT).show();
//                }
//
//                i++;
//            }
//
//            data.close();
//            Log.i("Location: ", LocationJson.toString());
//        }
//
//    }

    public boolean CheckInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isAvailable() &&
                connectivityManager.getActiveNetworkInfo().isConnected()) {

            return true;
        } else return false;
    }

}
