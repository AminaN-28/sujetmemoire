package com.example.bloodlineapp.Login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.example.bloodlineapp.AppDetails.Home;
import com.example.bloodlineapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.concurrent.TimeUnit;

import static com.example.bloodlineapp.Login.Login.ImageUri;

public class OTPVerify extends AppCompatActivity {

    Button Verifybtn;
    private PinView pinView;


    private User user;

    private static final String USERS = "users";

    private FirebaseUser mCurrentUser;//for member variable

    private FirebaseDatabase database, mDBase;

    private DatabaseReference mDatabase;
    private StorageReference mRoot;


    String username, url,userBloodG,userage,userweight,userpassword,useraddress,userphone;


    private static final String KEY_VERIFICATION_ID = "key_verification_id";

    // [START declare_auth]
    private FirebaseAuth mAuth; //Create an instance of FirebaseAuth

    // [END declare_auth]

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    String mverificationId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otpverify);

        Verifybtn = findViewById(R.id.buttonotp);
        pinView = findViewById(R.id.pinView);

        database = FirebaseDatabase.getInstance();
        mRoot = FirebaseStorage.getInstance().getReference();
        mDatabase = database.getReference(USERS);
        mDBase = FirebaseDatabase.getInstance();

        Verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final String pinViewRec = pinView.getText().toString();
                PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(mverificationId,pinViewRec);
                signInWithPhoneAuthCredential(phoneAuthCredential);
                Verify();

            }
        });
        // Restore instance state
        // put this code after starting phone number verification
        if (mverificationId == null && savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }

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
                Intent gohome = new Intent(getApplicationContext(), Home.class);
                gohome.putExtra("nom", username);
                gohome.putExtra("profile",url);
                gohome.putExtra("address",useraddress);
                gohome.putExtra("password",userpassword);
                gohome.putExtra("groupeS",userBloodG);
                gohome.putExtra("weight",userweight);
                gohome.putExtra("age",userage);
                gohome.putExtra("phonenumber",userphone);
                startActivity(gohome);


            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w("Pokemon35", "onVerificationFailed", e);
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
                Log.d("pokemon", "onCodeSent:" + verificationId);
                // FirebaseUser user = mAuth.getCurrentUser();
                //updateUI(user);

                // Save verification ID and resending token so we can use them later
                mverificationId = verificationId;
                PhoneAuthProvider.ForceResendingToken mResendToken = token;

                // ...
            }
        };
        Log.d("test","tester");


    }

    public void saveInFBStorage(final String id){

        mRoot.child("userProfile").child(id).putFile(ImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        mRoot.child("userProfile").child(id).getDownloadUrl()
                                .addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                         url = task.getResult().toString();
                                }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {


                    }
                });

    }



    private void Verify(){
        username = getIntent().getStringExtra("nom");
       userpassword = getIntent().getStringExtra("password");
       useraddress = getIntent().getStringExtra("address");
       userphone = getIntent().getStringExtra("phonenumber");
       userBloodG = getIntent().getStringExtra("groupeS");
       userweight = getIntent().getStringExtra("weight");
       userage = getIntent().getStringExtra("age");
         url = getIntent().getStringExtra("profile");
          Log.d("pika",userphone);
        // uploadtofirebase();

        Log.d("pika",userphone);


//        String id = mDatabase.push().getKey(); //donner une clé d'identification au user
    /*   saveInFBStorage(id);
        User user = new User( username, useraddress, userphone, userpassword, userBloodG, userweight, userage, url);
        mDatabase.child(id).setValue(user);*/
//    mDatabase.child(mCurrentUser.getUid()).setValue(user); //
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(userphone)       // Phone number to verify
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
                            Log.d("Pokemon3", "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("Pokemon2", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_VERIFICATION_ID,mverificationId);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mverificationId = savedInstanceState.getString(KEY_VERIFICATION_ID);

    }
}
