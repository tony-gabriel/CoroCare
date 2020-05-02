package com.example.coronahelpapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.LocationResult;

public class MyLocationService extends BroadcastReceiver {

    public static final String ACTION_PROCESS_UPDATE = "com.example.coronahelpapp.UPDATE_LOCATION";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent != null) {

            final String action = intent.getAction();

            if (ACTION_PROCESS_UPDATE.equals(action)) {

                LocationResult result = LocationResult.extractResult(intent);

                if (result != null) {

                    Location location = result.getLastLocation();
                    String location_string = new StringBuilder("" + location.getLatitude())
                            .append("/")
                            .append(location.getLongitude())
                            .toString();

                    try {
                        MainActivity.getInstance().showLocationUpdate(location_string);

                    } catch (Exception ex) {
                        Toast.makeText(context, location_string, Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }

    }
}
