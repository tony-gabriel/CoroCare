package com.example.coronahelpapp;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class Symptoms extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.viewColor));

        ImageView img1 = findViewById(R.id.img111);
        ImageView img2 = findViewById(R.id.img112);

        Picasso.get().load(R.drawable.sym2).into(img1);

        Picasso.get().load(R.drawable.sym_1).into(img2);
    }

    public void goBack(View view) {
        finish();
    }
}
