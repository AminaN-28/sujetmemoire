package com.example.bloodlineapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    private Button next;

    private EditText userName, userPhone, city, bloodg, userpassword;

    private String fname,phone,address,password, bloodG;

    private TextView logindirect;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private User user;

    private static final String USERS = "users";

    private FirebaseAuth mAuth; //Create an instance of FirebaseAuth

    //private FirebaseUser mCurrentUser;//for member variable

    private FirebaseDatabase database;

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        next = findViewById(R.id.button);
        userName = findViewById(R.id.username);
        userPhone = findViewById(R.id.userPhone);
        bloodg = findViewById(R.id.bloodgroup);
        city= findViewById(R.id.usercity);
        userpassword= findViewById(R.id.userpassword);
        logindirect = findViewById(R.id.logindirct);

        mAuth= FirebaseAuth.getInstance();//initialisation of instance
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USERS);
       // mCurrentUser = mAuth.getCurrentUser();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //insert data into firebase database
                if(validateFullName() | validatePassword() | validateAddress()  | validatePhone() | validateBlood() ) {
                    fname = userName.getText().toString();
                    address = city.getText().toString();
                    password =userpassword.getText().toString();
                    bloodG = bloodg.getText().toString();

                    user = new User(fname, address, phone, password, bloodG);

                   phone = userPhone.getText().toString();//convertir le numero en string
                    Intent logInt = new Intent(Login.this,OTPVerify.class);
                    logInt.putExtra("nom", userName.getText().toString());
                    logInt.putExtra("phonenumber", userPhone.getText().toString());
                    logInt.putExtra("address",city.getText().toString());
                    logInt.putExtra("password" ,userpassword.getText().toString());
                    logInt.putExtra("groupeS", bloodg.getText().toString());
                    //logInt.putExtra("string",verificationId);
                    //logInt.putExtra("forcing",token);
                    startActivity(logInt);

                }

            }
        });
    logindirect.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent logindirect= new Intent(Login.this, Connect.class);
            startActivity(logindirect);
        }
    });


        //PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);


    }





    //validate function
    // validation functions
    private boolean validateFullName(){
        String val = userName.getText().toString().trim();

        if (val.isEmpty()){
            userName.setError("Field  can not be empty");
            return false;
        }
        else {
            userName.setError(null);
            return true;
        }
    }
    private boolean validateBlood(){
        String val = bloodg.getText().toString().trim();
        String checkbloodg = "A-" + "A+" + "B+" + "AB+"+ "AB-" + "O+" + "O-"; // all blood group

        if (val.isEmpty()){
            bloodg.setError("Field  can not be empty");
            return false;
        }
        else if (!val.matches(checkbloodg)){
            userpassword.setError("Password should contain 8 characters! ");
            return false;
        }
        else
        {
            bloodg.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        /*return true;*/
        String val = userpassword.getText().toString().trim();
        String checkpassword = "^" +
                "(?=.*[a-zA-Z])" + //any letter
                 "(?=.*[@#$^&+=])" + //at least 1 special caracter
                "(?=\\S+$)" + // no white spaces
                ".{8,}" +   // at least 8 characters
                "$";
        if (val.isEmpty()) {
            userpassword.setError("Field  can not be empty");
            return false;
        }
        else if (!val.matches(checkpassword)) {
            userpassword.setError("Password should contain 8 characters! ");
            return false;
        }
        else {
            userpassword.setError(null);
            return true;
        }
    }

    private boolean validatePhone(){
        String val = userPhone.getText().toString().trim();
        if (val.isEmpty()){
            userPhone.setError("Field  can not be empty");
            return false;
        }
        else {
            userPhone.setError(null);
            return true;
        }
    }

    private boolean validateAddress(){
        String val = city.getText().toString().trim();
        if (val.isEmpty()){
            city.setError("Field  can not be empty");
            return false;
        }
        else {
            city.setError(null);
            return true;
        }
    }

}
