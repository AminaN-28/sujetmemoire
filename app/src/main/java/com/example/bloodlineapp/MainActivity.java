package com.example.bloodlineapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chaos.view.PinView;

public class MainActivity extends AppCompatActivity {

    private Button Startbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Startbtn = findViewById(R.id.button);

        Startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start = new Intent(MainActivity.this, Login.class);
                startActivity(start);
            }
        });


    }
}
