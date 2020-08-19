package com.example.bloodlineapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

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
                if(validateFullName() | validatePassword() | validateAddress()  | validatePhone() | validateBlood()  ) {
                    fname = userName.getText().toString();
                    address = city.getText().toString();
                    password =userpassword.getText().toString();
                    bloodG = bloodg.getText().toString();

                    user = new User(fname, address, phone,password,bloodG);

                   phone = userPhone.getText().toString();//convertir le numero en string
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+221" +phone,        // Phone number to verify
                            60,                 // Timeout duration
                            TimeUnit.SECONDS,   // Unit of timeout
                            Login.this,               // Activity (for callback binding)
                            mCallbacks);        // OnVerificationStateChangedCallbacks

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

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d("TAG", "onVerificationCompleted:" + credential);


                signInWithPhoneAuthCredential(credential);


            }


            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w("TAG", "onVerificationFailed", e);
               // Toast.makeText(Login.this, "Phone Number format invalid", Toast.LENGTH_LONG).show();

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }

                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("TAG", "onCodeSent:" + verificationId);
               // FirebaseUser user = mAuth.getCurrentUser();
                //updateUI(user);

                // Save verification ID and resending token so we can use them later
                String mVerificationId = verificationId;
                PhoneAuthProvider.ForceResendingToken mResendToken = token;

                Intent logInt = new Intent(Login.this,OTPVerify.class);

                logInt.putExtra("nom",userName.getText().toString());
                logInt.putExtra("phonenumber", userPhone.getText().toString());
                logInt.putExtra("groupeS", userName.getText().toString());
                logInt.putExtra("string",verificationId);
                logInt.putExtra("forcing",token);
                startActivity(logInt);
                // ...
            }
        };
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
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
        String val = userpassword.getText().toString().trim();
        String checkpassword = "^" +
                "(?=.*[a-zA-Z])" + //any letter
                //"(?=.*[@#$^&+=])" + at least 1 special caracter
                "(?=\\S+$)" + // no white spaces
                ".{8,}" +   // at least 8 characters
                "$";
        if (val.isEmpty()) {
            userpassword.setError("Field  can not be empty");
            return false;
        }
        /*else if (!val.matches(checkpassword)) {
            userpassword.setError("Password should contain 8 characters! ");
            return false;
        }*/
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
    public void updateUI(FirebaseUser currentUser) {
        String keyid = mDatabase.push().getKey();
        mDatabase.child(keyid).setValue(user); //adding user info to database
        Intent loginIntent = new Intent(this, Connect.class);
        startActivity(loginIntent);
    }

}
