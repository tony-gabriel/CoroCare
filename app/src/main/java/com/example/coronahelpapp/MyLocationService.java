package com.example.coronahelpapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.location.LocationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MyLocationService extends BroadcastReceiver {

    public static final String ACTION_PROCESS_UPDATE = "com.example.coronahelpapp.UPDATE_LOCATION";

    DatabaseReference mDataBase;
    FirebaseUser firebaseUser;
    String uid;

    Location location;
    Double homeLat, homeLong, currentLat, currentLong;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent != null) {

            final String action = intent.getAction();

            if (ACTION_PROCESS_UPDATE.equals(action)) {

                LocationResult result = LocationResult.extractResult(intent);

                if (result != null) {

                    location = result.getLastLocation();
                    String location_string = new StringBuilder("" + location.getLatitude())
                            .append("/")
                            .append(location.getLongitude())
                            .toString();


                    try {
                        MainActivity.getInstance().showLocationUpdate(location_string, location);

                    } catch (Exception ex) {

                        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        uid = firebaseUser.getUid();
                        mDataBase = FirebaseDatabase.getInstance().getReference().child("Users");


                        currentLat = location.getLatitude();
                        currentLong = location.getLongitude();

                        mDataBase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                homeLat = (double) dataSnapshot.child(uid).child("user_location").child("latitude").getValue();
                                homeLong = (double) dataSnapshot.child(uid).child("user_location").child("longitude").getValue();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                        if (homeLat != null || homeLong != null) {

                            Location startPoint = new Location("userHomeLocation");
                            startPoint.setLatitude(homeLat);
                            startPoint.setLongitude(homeLong);

                            Location endPoint = new Location(location);
                            endPoint.setLatitude(currentLat);
                            endPoint.setLongitude(currentLong);

                            double distance = startPoint.distanceTo(endPoint);

                            int distanceRd = (int) Math.round(distance);

                            Toast.makeText(context, "You are " + distanceRd + " meters away from home.", Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(context, "Home coordinates not available.", Toast.LENGTH_SHORT).show();
                        }

//                        Toast.makeText(context, location_string, Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }

    }
}
