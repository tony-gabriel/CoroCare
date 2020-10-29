package com.example.coronahelpapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.widget.Toast;

import com.google.android.gms.location.LocationResult;


public class MyLocationService extends BroadcastReceiver {

    public static final String ACTION_PROCESS_UPDATE = "com.example.coronahelpapp.UPDATE_LOCATION";
    Location location;
    Double homeLat, homeLong, currentLat, currentLong;
    String marker;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent != null) {

            final String action = intent.getAction();

            if (ACTION_PROCESS_UPDATE.equals(action)) {

                LocationResult result = LocationResult.extractResult(intent);

                if (result != null) {

                    location = result.getLastLocation();

                    try {
//                        MainActivity.getInstance().showLocationUpdate(location);


                    } catch (Exception ex) {

                        //  marker = MainActivity.getInstance().dateMarker;

                        try {
                            SharedPreferences prefs = context.getSharedPreferences("userHome", Context.MODE_PRIVATE);
                            homeLat = Double.parseDouble(prefs.getString("Latitude", "0.0"));
                            homeLong = Double.parseDouble(prefs.getString("Longitude", "0.0"));
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, "shared preference failed", Toast.LENGTH_SHORT).show();
                        }
                        if (homeLat != 0.0 && homeLong != 0.0) {

                            currentLat = location.getLatitude();
                            currentLong = location.getLongitude();

                            Location startPoint = new Location("userHomeLocation");
                            startPoint.setLatitude(homeLat);
                            startPoint.setLongitude(homeLong);

                            Location endPoint = new Location(location);
                            endPoint.setLatitude(currentLat);
                            endPoint.setLongitude(currentLong);

                            double distance = startPoint.distanceTo(endPoint);

                            int distanceRd = (int) Math.round(distance);

                            // Toast.makeText(context, "You are " + distanceRd + " meters away from home.", Toast.LENGTH_SHORT).show();

//                            if (distanceRd >= 100) {
//
//                                MainActivity.getInstance().SaveLocation(currentLat, currentLong, marker);
//                            }

//                        } else {

                            //  Toast.makeText(context, "Home coordinates not available.", Toast.LENGTH_SHORT).show();
                        }

//                        Toast.makeText(context, location_string, Toast.LENGTH_SHORT).show();

                    }

                }

            }

        }

    }

}
