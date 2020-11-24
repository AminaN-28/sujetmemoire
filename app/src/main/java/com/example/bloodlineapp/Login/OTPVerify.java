package com.example.bloodlineapp.Login;

import android.content.Intent;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.concurrent.TimeUnit;

public class OTPVerify extends AppCompatActivity {

    Button Verifybtn;
    private PinView pinView;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private User user;

    private static final String USERS = "users";

    private FirebaseAuth mAuth; //Create an instance of FirebaseAuth

    private FirebaseUser mCurrentUser;//for member variable

    private FirebaseDatabase database, mDBase;

    private DatabaseReference mDatabase;
    private StorageReference mRoot;

    String mverificationId;
    private static final String KEY_VERIFICATION_ID = "key_verification_id";



    String username, url,userBloodG,userage,userweight,userpassword,useraddress,userphone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otpverify);

        Verifybtn = findViewById(R.id.buttonotp);
        pinView = findViewById(R.id.pinView);

        mAuth= FirebaseAuth.getInstance();//initialisation of instance
        database = FirebaseDatabase.getInstance();
         mRoot = FirebaseStorage.getInstance().getReference();
         mDatabase = database.getReference(USERS);
         mDBase = FirebaseDatabase.getInstance();

      //  mCurrentUser = mAuth.getCurrentUser();


        Verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final String pinViewRec = pinView.getText().toString();
                PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(mverificationId,pinViewRec);

            }
        });
        /******A FAIREEEEEEEEEEEEEEEEEEEEEEEE ********/
        //LIBRAIRE TIME pour pouvoir avoir 2j avant 4j avant etc
        //Creer une base de donnée locale pour pouvoir recuperer son nom et son prenom en locale et pouvoir afficher le nom du user lors de l'alerte.

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
//                    getUser();
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
       Verify();
    }

    @Override
    protected void onStart() {
        super.onStart();
       // Verify();
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
       // uploadtofirebase();

        Log.d("pika",userphone);

 //String id = mDatabase.push().getKey(); //donner une clé d'identification au user
    /*  saveInFBStorage(id);
        User user = new User( username, useraddress, userphone, userpassword, userBloodG, userweight, userage, url);
        mDatabase.child(id).setValue(user);*/
//    mDatabase.child(mCurrentUser.getUid()).setValue(user); //
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+221" +userphone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }

   /*public void saveInFBStorage(final String id){

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
    }*/

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(OTPVerify.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Pokemon2", "signInWithCredential:success");

                            // Intent intent = new Intent(OTPVerify.this, Home.class);
                            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            //startActivity(intent);
                            FirebaseUser user = mAuth.getCurrentUser();
                           // getUser();

                            // updateUI(user);

                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("Pokemon3", "signInWithCredential:failure", task.getException());

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    public void updateUI(FirebaseUser currentUser) {
        String keyid = mDatabase.push().getKey();
        mDatabase.child(keyid).setValue(user); //adding user info to database
        Intent loginIntent = new Intent(this, Connect.class);
        startActivity(loginIntent);
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

    /*private void getUser(){
        mCurrentUser = mAuth.getCurrentUser();
        DatabaseReference mGetReference = mDBase.getReference().child("users").child(mCurrentUser.getUid());
        Log.d("salut", mCurrentUser.getUid());

        mGetReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d("salut", snapshot.getValue().toString());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/


}
