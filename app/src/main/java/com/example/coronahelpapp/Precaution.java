package com.example.coronahelpapp;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class Precaution extends AppCompatActivity {

    ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_precaution);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.viewColor));

        imageView1 = findViewById(R.id.img1);
        imageView2 = findViewById(R.id.img2);
        imageView3 = findViewById(R.id.img3);
        imageView4 = findViewById(R.id.img4);
        imageView5 = findViewById(R.id.img5);
        imageView6 = findViewById(R.id.img6);

        LoadImage();
    }

    private void LoadImage() {

        Picasso.get()
                .load(R.drawable.wash_hands)
                .into(imageView1);

        Picasso.get()
                .load(R.drawable.distancing)
                .into(imageView2);

        Picasso.get()
                .load(R.drawable.face)
                .into(imageView3);

        Picasso.get()
                .load(R.drawable.tissue)
                .into(imageView4);

        Picasso.get()
                .load(R.drawable.home)
                .into(imageView5);

        Picasso.get()
                .load(R.drawable.inform)
                .into(imageView6);

    }

    public void imageBack(View view) {
        finish();
    }
}
