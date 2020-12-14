package com.example.bloodlineapp.Login;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.example.bloodlineapp.AppDetails.Home;
import com.example.bloodlineapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class OTPVerify extends AppCompatActivity {

    Button Verifybtn, changelang;//BOUTON POUR ENVOYER AUSSI
    private PinView pinView;


    private User user;

    private static final String USERS = "users";

    private FirebaseUser mCurrentUser;//for member variable

    private FirebaseDatabase database, mDBase;

    private DatabaseReference mDatabase;


    String username, userprofile,userBloodG,userage,userweight,userpassword,useraddress,userphone;


    private static final String KEY_VERIFICATION_ID = "key_verification_id";

    // [START declare_auth]
    private FirebaseAuth mAuth; //Create an instance of FirebaseAuth

    // [END declare_auth]

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    public static Uri ImageUri;

    String mverificationId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otpverify);


         //changelang = findViewById(R.id.button1);

/*        changelang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Show AlertDialog to display list of languages , one only can be selected
                showChangeLangageDialog();

            }
        });*/


        Verifybtn = findViewById(R.id.buttonotp);
        pinView = findViewById(R.id.pinView);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USERS);
        mDBase = FirebaseDatabase.getInstance();
        mAuth =FirebaseAuth.getInstance();




        Verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final String pinViewRec = pinView.getText().toString();
              PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(mverificationId,pinViewRec);
              signInWithPhoneAuthCredential(phoneAuthCredential);

            }
        });
        // Restore instance state
        // put this code after starting phone number verification
        /*if (mverificationId == null && savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }*/


        Log.d("test","tester");

        //Verify();


    }


    private void Verify(){
         Log.d("builbi","Before");
        username = getIntent().getStringExtra("nom");
       userpassword = getIntent().getStringExtra("password");
       useraddress = getIntent().getStringExtra("address");
       userphone = getIntent().getStringExtra("phonenumber");
       userBloodG = getIntent().getStringExtra("groupeS");
       userweight = getIntent().getStringExtra("weight");
       userage = getIntent().getStringExtra("age");
        userprofile = getIntent().getStringExtra("profile");


        Log.d("builbi","After");
         Log.d("pika",userphone);




//    mDatabase.child(mCurrentUser.getUid()).setValue(user); //

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+221"+userphone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d("Pokemon3", "signInWithCredential:success");
                            Intent gohome = new Intent(OTPVerify.this, Home.class);
                            gohome.putExtra("nom", username);
                            gohome.putExtra("profile",userprofile);
                            gohome.putExtra("address",useraddress);
                            gohome.putExtra("password",userpassword);
                            gohome.putExtra("groupeS",userBloodG);
                            gohome.putExtra("weight",userweight);
                            gohome.putExtra("age",userage);
                            gohome.putExtra("phonenumber",userphone);

                            User user = new User( username, useraddress, userphone, userpassword, userBloodG, userweight, userage, userprofile);
                            mDatabase.child(mAuth.getUid()).setValue(user);

                            startActivity(gohome);
                            finish();

                            FirebaseUser users = task.getResult().getUser();

                            // ...
                        }
                        else {
                            // Sign in failed, display a message and update the UI
                            Log.w("Pokemon2", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(OTPVerify.this,"Incorrect OTP",Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                });
    }


    @Override
    protected void onStart() {
        super.onStart();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {// si le numero de telephone a ete verifie avec succes
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically.
                //     detect the incoming verification SMS and perform verification without
                //     user action.

                //  print(credential.getSmsCode());
                Log.d("Pokemon2", "onVerificationCompleted:" + credential.getSmsCode());
                signInWithPhoneAuthCredential(credential);


            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w("Pokemon35", "onVerificationFailed", e);
                // Toast.makeText(Login.this, "Phone Number format invalid", Toast.LENGTH_LONG).show();
                e.printStackTrace();


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
                Log.d("pokemon", "onCodeSent:" + verificationId);
                // FirebaseUser user = mAuth.getCurrentUser();
                //updateUI(user);

                // Save verification ID and resending token so we can use them later
                mverificationId = verificationId;
                PhoneAuthProvider.ForceResendingToken mResendToken = token;

                // ...
            }
        };
        Verify();

    }

    private void showChangeLangageDialog() {
        final String [] list = {"French", "Català", "جزائري", "موريتاني", "English Us"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(OTPVerify.this);
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
