package com.example.coronahelpapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Registration extends AppCompatActivity {
    FirebaseUser firebaseUser;


    EditText fullname;
    EditText mobile;
    Button proceed;
    RadioButton rbtMale;
    RadioButton rbtFemale;
    RadioButton rbtHome;
    RadioButton rbtAway;
    DatabaseReference mDatabase;
    String health_Status = "healthy";
    String ImageUri="";

    public  String userId;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth=FirebaseAuth.getInstance();

        fullname = findViewById(R.id.fullname);
        mobile = findViewById(R.id.mobile);
        proceed = findViewById(R.id.proceed);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");



        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String username = fullname.getText().toString();
                final String mobileNumber = mobile.getText().toString();
                final String health="healthy";



                if (username.isEmpty()) {
                    fullname.requestFocus();
                    fullname.setError("please enter a value");
                    return;

                }

                if (mobileNumber.isEmpty()) {
                    mobile.requestFocus();
                    mobile.setError("please enter a value");
                    return;

                }
                final ProgressDialog progressDialog = new ProgressDialog(Registration.this);
                progressDialog.setTitle("Loading...");
                progressDialog.show();


                  mAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {

                          if (task.isSuccessful()){



                              User user = new User(username,mobileNumber, health_Status, ImageUri);

                             FirebaseDatabase.getInstance().getReference("Users").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance()
                                      .getCurrentUser())).getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                  @Override
                                  public void onComplete(Task<Void> task) {



                                      if (task.isSuccessful()) {


                                      progressDialog.dismiss();
                                      Gotoprofile();


                          }
                          else {

                              progressDialog.dismiss();

                              Toast.makeText(Registration.this, "Registration Failed", Toast.LENGTH_LONG).show();


                          }

                      }


                  });





            }
        }




        });




    }

    private void Gotoprofile() {

                Intent intent = new Intent(Registration.this, ProfileImageReg.class);
                startActivity(intent);
                finish();


    }

    });
    }
}
