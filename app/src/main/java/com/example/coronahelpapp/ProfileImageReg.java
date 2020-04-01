package com.example.coronahelpapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ProfileImageReg extends AppCompatActivity {

    RoundedImageView getImage, profileImage;
    Button saveProfile;

    public static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getImage = findViewById(R.id.getImage);
        profileImage = findViewById(R.id.profile_main);

        saveProfile = findViewById(R.id.saveImage);


        getImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select a Profile Picture"), PICK_IMAGE);
            }
        });

        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO: set Firebase codes here
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {

            try {

                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                profileImage.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {

                e.printStackTrace();
            }
        }
    }
}
