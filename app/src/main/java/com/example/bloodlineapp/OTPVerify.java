package com.example.bloodlineapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.chaos.view.PinView;

public class OTPVerify extends AppCompatActivity {

    Button Verifybtn;
    private PinView pinView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otpverify);

        Verifybtn = findViewById(R.id.buttonotp);
        pinView = findViewById(R.id.pinView);

        Verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gohome = new Intent(OTPVerify.this, Home.class);
                startActivity(gohome);
            }
        });
    }

}
