package com.example.bloodlineapp.Onboarding;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bloodlineapp.Login.Login;
import com.example.bloodlineapp.R;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button Startbtn , changelang, testbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadLocale();
        Startbtn = findViewById(R.id.button);

       // changelang = findViewById(R.id.button1);


        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle(getResources().getString(R.string.app_name));


        Startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start = new Intent(MainActivity.this, Login.class);
                startActivity(start);
                finish();
            }
        });

        /*testbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent skip = new Intent(MainActivity.this, Home.class);
                startActivity(skip);

            }
        });*/

       /* changelang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Show AlertDialog to display list of languages , one only can be selected
                showChangeLangageDialog();

            }
        });*/
    }

    //We have first to create separate strings.xml for each language
    private void showChangeLangageDialog() {
        final String [] list = {"French", "Català", "جزائري", "موريتاني", "English Us"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        mBuilder.setTitle("Choose Language.....");
        mBuilder.setSingleChoiceItems(list, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0){
                    setLocale("fr");
                    recreate();
                }
               else if (which == 1){
                    setLocale("ca");
                    recreate();
                }
                else if (which == 2){
                    setLocale("ar");
                    recreate();
                }
                else if (which == 3){
                    setLocale("ar");
                    recreate();
                }
                else
                {
                    setLocale("en");
                    recreate();
                }

                //dismiss alertwhen item selected

                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = mBuilder.create();

        alertDialog.show();

    }

    private void setLocale(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration configuration = new Configuration();
        configuration.locale =locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());

        //saved data to shared preferences
        SharedPreferences.Editor editor =  getSharedPreferences("Settinngs", MODE_PRIVATE).edit();
        editor.putString("My Language", language);
        editor.apply();
    }

    public void loadLocale(){
        SharedPreferences preferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String lang = preferences.getString("My Language","");
        setLocale(lang);
    }
}
