package com.example.bloodlineapp.Onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bloodlineapp.AppDetails.Home;
import com.example.bloodlineapp.Login.Login;
import com.example.bloodlineapp.R;

public class MainActivity extends AppCompatActivity {

    private Button Startbtn , Skipbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Startbtn = findViewById(R.id.button);

        Skipbtn = findViewById(R.id.button1);

        Startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start = new Intent(MainActivity.this, Login.class);
                startActivity(start);
            }
        });

        Skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent skip = new Intent(MainActivity.this, Home.class);
                startActivity(skip);

            }
        });
    }
}
