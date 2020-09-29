package com.example.bloodlineapp.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bloodlineapp.AppDetails.Home;
import com.example.bloodlineapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;

public class Connect extends AppCompatActivity {

    Button connectbtn;

    EditText fname, pwd;
    TextView notregistered;
    private FirebaseAuth mAuth;
    String username, userpassword;


    PhoneAuthCredential credential;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect);

        fname = findViewById(R.id.username);
        pwd = findViewById(R.id.userpassword);
        connectbtn = findViewById(R.id.buttonconnect);
        notregistered= findViewById(R.id.logindirct);

        username = getIntent().getStringExtra("nom");
        userpassword = getIntent().getStringExtra("password");

        notregistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goregister = new Intent(Connect.this, Login.class);
                startActivity(goregister);
            }
        });

        connectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fname.equals(username) && pwd.getText().toString().matches(userpassword)) {
                    Intent gohome = new Intent(Connect.this, Home.class);
                    startActivity(gohome);
                }
                else {
                    pwd.setError("Field  can not be empty");
                    pwd.setError("Your password is not correct");
                }
            }
        });


    }
}




