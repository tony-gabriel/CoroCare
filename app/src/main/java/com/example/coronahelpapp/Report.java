package com.example.coronahelpapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Report extends AppCompatActivity {

    final String[] states = {"<---Select your state--->", "NCDC (National", "Akwa Ibom", "Anambra", "Bayelsa",
            "Bauchi", "Cross River", "Delta", "Enugu", "Ekiti", "Edo", "FCT", "Imo", "Kano", "Kaduna",
            "Lagos", "Niger", "Osun", "Ogun", "Ondo", "Oyo", "Plateau", "Rivers", "Sokoto", "Yobe", "Zamfara"};

    final String[] blank = {""};
    final String[] ncdc = {"08009700010"};
    final String[] akwa = {"08028442194", "08037934966", "09023330092"};
    final String[] anambra = {"08145434416"};
    final String[] bayelsa = {"08039216821", "07019304970", "08151693570"};
    final String[] bauchi = {"08023909309", "08032717887", "08059600898", "08080330216", "08036911698"};
    final String[] cross = {"09036281412"};
    final String[] delta = {"08033521960", "08035078541", "08030758179", "09065031241"};
    final String[] enugu = {"117", "112", "08182555550"};
    final String[] ekiti = {"112", "09062970434", "09062970435", "09062970436"};
    final String[] edo = {"08084096723", "08064258163", "08035835529"};
    final String[] fct = {"08099936312", "07080631500"};
    final String[] imo = {"08099555577", "07087110839"};
    final String[] kano = {"09093995333", "09093995444"};
    final String[] kaduna = {"08025088304", "08032401473", "08035871662", "08037808191", "08036045755", "08027396344"};
    final String[] lagos = {"08000267662"};
    final String[] niger = {"08038246018", "09093093642", "08077213070"};
    final String[] osun = {"293", "08035025692", "08033908772", "08056456250"};
    final String[] ogun = {"08188978393", "08188978392"};
    final String[] ondo = {"08002684319", "07002684319"};
    final String[] oyo = {"08095394000"};
    final String[] plateau = {"07032864444", "08035422711", "08065486416", "08035779917"};
    final String[] rivers = {"08056109538", "08031888093", "08033124314"};
    final String[] sokoto = {"07031935037", "08022069567", "08035074228", "08036394462"};
    final String[] yobe = {"08131834764", "07041116027"};
    final String[] zamfara = {"08035626731", "08035161538", "08161330774", "08065408696", "08105009888", "08063075385"};

    FloatingActionButton fab;
    Spinner state, numbers;
    String callNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        state = findViewById(R.id.spinner_main);
        numbers = findViewById(R.id.spinner_sub);
        fab = findViewById(R.id.floatingActionButton);

        CheckPhonePermission();


        ArrayAdapter<String> state_adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_dropdown_item, states);
        state.setAdapter(state_adapter);


        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {

                    case 0:
                        final ArrayAdapter<String> blankAdp;
                        blankAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, blank);
                        numbers.setAdapter(blankAdp);
                        break;

                    case 1:
                        final ArrayAdapter<String> ncdcAdp;
                        ncdcAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, ncdc);
                        numbers.setAdapter(ncdcAdp);
                        break;

                    case 2:
                        final ArrayAdapter<String> akwaAdp;
                        akwaAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, akwa);
                        numbers.setAdapter(akwaAdp);
                        break;

                    case 3:
                        final ArrayAdapter<String> anambraAdp;
                        anambraAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, anambra);
                        numbers.setAdapter(anambraAdp);
                        break;

                    case 4:
                        final ArrayAdapter<String> bayelsaAdp;
                        bayelsaAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, bayelsa);
                        numbers.setAdapter(bayelsaAdp);
                        break;

                    case 5:
                        final ArrayAdapter<String> bauchiAdp;
                        bauchiAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, bauchi);
                        numbers.setAdapter(bauchiAdp);
                        break;

                    case 6:
                        final ArrayAdapter<String> crossAdp;
                        crossAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, cross);
                        numbers.setAdapter(crossAdp);
                        break;

                    case 7:
                        final ArrayAdapter<String> deltaAdp;
                        deltaAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, delta);
                        numbers.setAdapter(deltaAdp);
                        break;

                    case 8:
                        final ArrayAdapter<String> enuguAdp;
                        enuguAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, enugu);
                        numbers.setAdapter(enuguAdp);
                        break;

                    case 9:
                        final ArrayAdapter<String> ekitiAdp;
                        ekitiAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, ekiti);
                        numbers.setAdapter(ekitiAdp);
                        break;

                    case 10:
                        final ArrayAdapter<String> edoAdp;
                        edoAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, edo);
                        numbers.setAdapter(edoAdp);
                        break;

                    case 11:
                        final ArrayAdapter<String> fctAdp;
                        fctAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, fct);
                        numbers.setAdapter(fctAdp);
                        break;

                    case 12:
                        final ArrayAdapter<String> imoAdp;
                        imoAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, imo);
                        numbers.setAdapter(imoAdp);
                        break;

                    case 13:
                        final ArrayAdapter<String> kanoAdp;
                        kanoAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, kano);
                        numbers.setAdapter(kanoAdp);
                        break;

                    case 14:
                        final ArrayAdapter<String> kadunaAdp;
                        kadunaAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, kaduna);
                        numbers.setAdapter(kadunaAdp);
                        break;

                    case 15:
                        final ArrayAdapter<String> lagosAdp;
                        lagosAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, lagos);
                        numbers.setAdapter(lagosAdp);
                        break;

                    case 16:
                        final ArrayAdapter<String> nigerAdp;
                        nigerAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, niger);
                        numbers.setAdapter(nigerAdp);
                        break;

                    case 17:
                        final ArrayAdapter<String> osunAdp;
                        osunAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, osun);
                        numbers.setAdapter(osunAdp);
                        break;

                    case 18:
                        final ArrayAdapter<String> ogunAdp;
                        ogunAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, ogun);
                        numbers.setAdapter(ogunAdp);
                        break;

                    case 19:
                        final ArrayAdapter<String> ondoAdp;
                        ondoAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, ondo);
                        numbers.setAdapter(ondoAdp);
                        break;

                    case 20:
                        final ArrayAdapter<String> oyoAdp;
                        oyoAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, oyo);
                        numbers.setAdapter(oyoAdp);
                        break;

                    case 21:
                        final ArrayAdapter<String> plateauAdp;
                        plateauAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, plateau);
                        numbers.setAdapter(plateauAdp);
                        break;

                    case 22:
                        final ArrayAdapter<String> riversAdp;
                        riversAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, rivers);
                        numbers.setAdapter(riversAdp);
                        break;

                    case 23:
                        final ArrayAdapter<String> sokotoAdp;
                        sokotoAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, sokoto);
                        numbers.setAdapter(sokotoAdp);
                        break;

                    case 24:
                        final ArrayAdapter<String> yobeAdp;
                        yobeAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, yobe);
                        numbers.setAdapter(yobeAdp);
                        break;

                    case 25:
                        final ArrayAdapter<String> zamfaraAdp;
                        zamfaraAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, zamfara);
                        numbers.setAdapter(zamfaraAdp);
                        break;


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        numbers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                callNumber = numbers.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (callNumber.isEmpty()){

                    Toast.makeText(getApplicationContext(),
                            "Please select your state and a number", Toast.LENGTH_LONG).show();
                }else {

                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + callNumber));
                    if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 10);
                        return;
                    }
                    startActivity(intent);
                }

            }
        });
    }

    private void CheckPhonePermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 10);
        }

    }

    public void back(View view) {
        Intent back =new Intent (Report.this, MainActivity.class);
        startActivity(back);
        finish();
    }
}
