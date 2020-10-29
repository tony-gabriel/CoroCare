package com.example.coronahelpapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Report extends AppCompatActivity {

    final String[] states = {"<---Select your state--->", "NCDC (National)", "Abia", "Adamawa", "Akwa Ibom", "Anambra", "Bauchi", "Bayelsa", "Benue",
            "Borno", "Cross River", "Delta", "Ebonyi", "Edo", "Ekiti", "Enugu", "FCT", "Gombe", "Imo", "Jigawa", "Kaduna", "Kano", "Kastina", "Kebbi",
            "Kogi", "Kwara", "Lagos", "Nasarawa", "Niger", "Ogun", "Ondo", "Osun", "Oyo", "Plateau", "Rivers", "Sokoto", "Taraba", "Yobe", "Zamfara"};

    final String[] blank = {""};
    final String[] ncdc = {"08009700010"};
    final String[] adamawa = {"08031230359", "07080601139", "08115850085", "07025040415", "09044235334"};
    final String[] abia = {"07002242362"};
    final String[] akwa = {"08028442194", "08037934966", "09023330092", "08189411111",
            "09045575515",
            "07035211919"};
    final String[] anambra = {"08145434416", "09034728047",
            "09034668319",
            "08163594310",
            "09034663273",
            "08117567363",
            "09033805959"};
    final String[] ebonyi = {"09020332489",
            "08159279460",
            "07045910340",
            "07085763054",
            "09026434547"};
    final String[] bayelsa = {"08039216821", "07019304970", "08151693570"};
    final String[] benue = {"09018602439", "07025031214"};
    final String[] bauchi = {"08023909309", "08032717887", "08059600898", "08080330216", "08036911698"};
    final String[] cross = {"09036281412"};
    final String[] delta = {"08033521960", "08035078541", "08030758179", "09065031241"};
    final String[] enugu = {"117", "112", "08182555550", "09022333833"};
    final String[] ekiti = {"112", "09062970434", "09062970435", "09062970436"};
    final String[] edo = {"08084096723", "08064258163", "08035835529"};
    final String[] fct = {"08099936312", "07080631500", "08099936313", "08099936314", "08031230330", "08031230508"};
    final String[] gombe = {"08103371257", "07026256569", "07045257107", "07025227843", "07026761392", "07026799901", "07042145504"};
    final String[] imo = {"08099555577", "07087110839"};
    final String[] jigawa = {"08035997118", "08036440522", "08069323005", "08038806682", "07035997118", "08038629331"};
    final String[] kano = {"09093995333", "09093995444", "08039704476", "08037038597"};
    final String[] kaduna = {"08025088304", "08032401473", "08035871662", "08037808191", "08036045755", "08027396344"};
    final String[] kastina = {"09035037114", "09047092428", "08065635686", "08065568805"};
    final String[] kebi = {"08036782507",
            "08036074588",
            "08032907601",
            "07035606421",
            "08067677723",
            "08167597029",
            "08083400849", "07046352309",
            "07046407663",
            "07046935560"};
    final String[] kogi = {"07088292249", "08150953486", "08095227003", "07043402122"};
    final String[] kwara = {"09062010001", "09062010002"};
    final String[] lagos = {"08023169485",
            "08033565529",
            "08052817243",
            "08028971864",
            "08059758886",
            "08035387653", "08000267662"};
    final String[] nasarawa = {"08036018579", "08035871718", "08033254549", "08036201904", "08032910826", "08121243191"};
    final String[] niger = {" 09010999909", "09010999910", "08077213070"};
    final String[] osun = {"293", "08035025692", "08033908772", "08056456250"};
    final String[] ogun = {"08188978393", "08188978392", "08001235678"};
    final String[] ondo = {"08002684319", "07002684319", "07012684319"};
    final String[] oyo = {"08095394000", "08078288999", "08078288800"};
    final String[] plateau = {"07032864444", "08035422711", "08065486416", "08035779917"};
    final String[] rivers = {"08056109538", "08031888093", "08033124314", "09062277699", "08102900000"};
    final String[] sokoto = {"07031935037", "08022069567", "08035074228", "08036394462"};
    final String[] taraba = {"08065508675", "08032501165", "08039359368", "08037450227"};
    final String[] yobe = {"08131834764", "07041116027"};
    final String[] zamfara = {"08035626731", "08035161538", "08161330774", "08065408696", "08105009888", "08063075385"};
    final String[] borno = {"08088159881", "080099999999"};

    FloatingActionButton fab;
    Spinner state, numbers;
    String callNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.viewColor));

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
                        final ArrayAdapter<String> abiaAdp;
                        abiaAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, abia);
                        numbers.setAdapter(abiaAdp);
                        break;

                    case 3:
                        final ArrayAdapter<String> adamawaAdp;
                        adamawaAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, adamawa);
                        numbers.setAdapter(adamawaAdp);
                        break;

                    case 4:
                        final ArrayAdapter<String> akwaAdp;
                        akwaAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, akwa);
                        numbers.setAdapter(akwaAdp);
                        break;

                    case 5:
                        final ArrayAdapter<String> anambraAdp;
                        anambraAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, anambra);
                        numbers.setAdapter(anambraAdp);
                        break;

                    case 6:
                        final ArrayAdapter<String> bauchiAdp;
                        bauchiAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, bauchi);
                        numbers.setAdapter(bauchiAdp);
                        break;

                    case 7:
                        final ArrayAdapter<String> bayelsaAdp;
                        bayelsaAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, bayelsa);
                        numbers.setAdapter(bayelsaAdp);
                        break;

                    case 8:
                        final ArrayAdapter<String> benueAdp;
                        benueAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, benue);
                        numbers.setAdapter(benueAdp);
                        break;

                    case 9:
                        final ArrayAdapter<String> bornoAdp;
                        bornoAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, borno);
                        numbers.setAdapter(bornoAdp);
                        break;

                    case 10:
                        final ArrayAdapter<String> crossAdp;
                        crossAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, cross);
                        numbers.setAdapter(crossAdp);
                        break;

                    case 11:
                        final ArrayAdapter<String> deltaAdp;
                        deltaAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, delta);
                        numbers.setAdapter(deltaAdp);
                        break;

                    case 12:
                        final ArrayAdapter<String> ebonyiAdp;
                        ebonyiAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, ebonyi);
                        numbers.setAdapter(ebonyiAdp);
                        break;

                    case 13:
                        final ArrayAdapter<String> edoAdp;
                        edoAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, edo);
                        numbers.setAdapter(edoAdp);
                        break;

                    case 14:
                        final ArrayAdapter<String> ekitiAdp;
                        ekitiAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, ekiti);
                        numbers.setAdapter(ekitiAdp);
                        break;

                    case 15:
                        final ArrayAdapter<String> enuguAdp;
                        enuguAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, enugu);
                        numbers.setAdapter(enuguAdp);
                        break;

                    case 16:
                        final ArrayAdapter<String> fctAdp;
                        fctAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, fct);
                        numbers.setAdapter(fctAdp);
                        break;

                    case 17:
                        final ArrayAdapter<String> gombeAdp;
                        gombeAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, gombe);
                        numbers.setAdapter(gombeAdp);
                        break;

                    case 18:
                        final ArrayAdapter<String> imoAdp;
                        imoAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, imo);
                        numbers.setAdapter(imoAdp);
                        break;

                    case 19:
                        final ArrayAdapter<String> jigawaAdp;
                        jigawaAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, jigawa);
                        numbers.setAdapter(jigawaAdp);
                        break;

                    case 20:
                        final ArrayAdapter<String> kadunaAdp;
                        kadunaAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, kaduna);
                        numbers.setAdapter(kadunaAdp);
                        break;

                    case 21:
                        final ArrayAdapter<String> kanoAdp;
                        kanoAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, kano);
                        numbers.setAdapter(kanoAdp);
                        break;

                    case 22:
                        final ArrayAdapter<String> kastinaAdp;
                        kastinaAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, kastina);
                        numbers.setAdapter(kastinaAdp);
                        break;

                    case 23:
                        final ArrayAdapter<String> kebbiAdp;
                        kebbiAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, kebi);
                        numbers.setAdapter(kebbiAdp);
                        break;

                    case 24:
                        final ArrayAdapter<String> kogiAdp;
                        kogiAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, kogi);
                        numbers.setAdapter(kogiAdp);
                        break;

                    case 25:
                        final ArrayAdapter<String> kwaraAdp;
                        kwaraAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, kwara);
                        numbers.setAdapter(kwaraAdp);
                        break;

                    case 26:
                        final ArrayAdapter<String> lagosAdp;
                        lagosAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, lagos);
                        numbers.setAdapter(lagosAdp);
                        break;

                    case 27:
                        final ArrayAdapter<String> nasaAdp;
                        nasaAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, nasarawa);
                        numbers.setAdapter(nasaAdp);
                        break;

                    case 28:
                        final ArrayAdapter<String> nigerAdp;
                        nigerAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, niger);
                        numbers.setAdapter(nigerAdp);
                        break;

                    case 29:
                        final ArrayAdapter<String> ogunAdp;
                        ogunAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, ogun);
                        numbers.setAdapter(ogunAdp);
                        break;

                    case 30:
                        final ArrayAdapter<String> ondoAdp;
                        ondoAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, ondo);
                        numbers.setAdapter(ondoAdp);
                        break;

                    case 31:
                        final ArrayAdapter<String> osunAdp;
                        osunAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, osun);
                        numbers.setAdapter(osunAdp);
                        break;

                    case 32:
                        final ArrayAdapter<String> oyoAdp;
                        oyoAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, oyo);
                        numbers.setAdapter(oyoAdp);
                        break;

                    case 33:
                        final ArrayAdapter<String> platueAdp;
                        platueAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, plateau);
                        numbers.setAdapter(platueAdp);
                        break;

                    case 34:
                        final ArrayAdapter<String> riversAdp;
                        riversAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, rivers);
                        numbers.setAdapter(riversAdp);
                        break;

                    case 35:
                        final ArrayAdapter<String> sokotoAdp;
                        sokotoAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, sokoto);
                        numbers.setAdapter(sokotoAdp);
                        break;

                    case 36:
                        final ArrayAdapter<String> tarabaAdp;
                        tarabaAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, taraba);
                        numbers.setAdapter(tarabaAdp);
                        break;

                    case 37:
                        final ArrayAdapter<String> yobeAdp;
                        yobeAdp = new ArrayAdapter<>
                                (Report.this, android.R.layout.simple_spinner_dropdown_item, yobe);
                        numbers.setAdapter(yobeAdp);
                        break;

                    case 38:
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

                if (callNumber.isEmpty()) {

                    Toast.makeText(getApplicationContext(),
                            "Please select your state and a number", Toast.LENGTH_LONG).show();
                } else {

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
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 10);
        }

    }

    public void back(View view) {
        finish();
    }
}
