package com.example.coronahelpapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class Splash extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();

        firebaseUser = mAuth.getCurrentUser();

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    setAlarm();

                    if (firebaseUser != null) {
                        Intent intent = new Intent(Splash.this, MainActivity.class);

                        startActivity(intent);

                    } else {
                        Intent intent = new Intent(Splash.this, Registration.class);

                        startActivity(intent);
                    }

                }
            }
        };
        thread.start();

    }

    @Override
    protected void onPause() {
        super.onPause();

        finish();
    }

    private void setAlarm() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 07);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 00);

        Intent intent = new Intent(getApplicationContext(), Notification_Receiver.class);


        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }

}