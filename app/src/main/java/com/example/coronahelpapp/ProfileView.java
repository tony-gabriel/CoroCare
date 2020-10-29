package com.example.coronahelpapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class ProfileView extends AppCompatActivity {

    ImageView profileImage, errImage;
    TextView nameText, genderText, ageText, phoneText;

    View view;
    RelativeLayout err, dtl;

    DatabaseReference mDataBase;
    FirebaseUser firebaseUser;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        profileImage = findViewById(R.id.profile_main1);
        errImage = findViewById(R.id.error_img);

        nameText = findViewById(R.id.name1);
        genderText = findViewById(R.id.gender_p);
        ageText = findViewById(R.id.age_p);
        phoneText = findViewById(R.id.phone_p);

        view = findViewById(R.id.view11);
        err = findViewById(R.id.err_layout);
        dtl = findViewById(R.id.dtl_layout);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDataBase = FirebaseDatabase.getInstance().getReference().child("Users");
        uid = firebaseUser.getUid();

        LoadPage();

    }

    private void LoadPage() {

        if (CheckInternet()) {

            view.setVisibility(View.VISIBLE);
            dtl.setVerticalScrollbarPosition(View.VISIBLE);
            profileImage.setVisibility(View.VISIBLE);
            nameText.setVisibility(View.VISIBLE);
            err.setVisibility(View.GONE);

            try {
                run();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {

            LoadErrorPage();
        }
    }


    void run() throws IOException {

        mDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String User_name = dataSnapshot.child(uid).child("User_Name").getValue(String.class);
                String User_gender = dataSnapshot.child(uid).child("User_gender").getValue(String.class);
                String User_age = dataSnapshot.child(uid).child("User_Age").getValue(String.class);
                String User_phone = dataSnapshot.child(uid).child("User_phone").getValue(String.class);
                String User_pics = dataSnapshot.child(uid).child("ImageUri").getValue(String.class);

                nameText.setText(User_name);
                genderText.setText("Gender: " + User_gender);
                ageText.setText("Age: " + User_age);
                phoneText.setText("Phone Number: " + User_phone);

                try {
                    Picasso.get()
                            .load(User_pics)
                            .error(R.drawable.img_profile)
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

    public boolean CheckInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isAvailable() &&
                connectivityManager.getActiveNetworkInfo().isConnected()) {

            return true;
        } else {

            return false;
        }

    }

    private void LoadErrorPage() {
        view.setVisibility(View.GONE);
        dtl.setVerticalScrollbarPosition(View.GONE);
        profileImage.setVisibility(View.GONE);
        nameText.setVisibility(View.GONE);
        err.setVisibility(View.VISIBLE);

        Picasso.get()
                .load(R.drawable.no_connection1)
                .into(errImage);

        err.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadPage();
            }
        });
    }

    public void back(View view) {
        finish();
    }

}
