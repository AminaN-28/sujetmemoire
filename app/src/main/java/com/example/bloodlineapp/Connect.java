package com.example.bloodlineapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;

public class Connect extends AppCompatActivity {

    Button connectbtn;

    EditText fname, pwd;

    private FirebaseAuth mAuth;
    private String fullname, password;


    PhoneAuthCredential credential;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect);

        fname = findViewById(R.id.username);

        pwd = findViewById(R.id.userpassword);

        connectbtn = findViewById(R.id.buttonconnect);

        mAuth = FirebaseAuth.getInstance();

// final PhoneAuthCredential credential = PhoneAuthProvider.getCredential(fullname, password);

        //checking if user is logged in
       /* if (mAuth.getCurrentUser() != null) {
            updateUI(mAuth.getCurrentUser());
        }*/

        final  String username = getIntent().getStringExtra("nom");
        final String userpassword = getIntent().getStringExtra("password");

        connectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullname = fname.getText().toString();
                password = pwd.getText().toString();

                if (password.matches(userpassword) && fullname.matches(username))
                {
                    Intent HomePge = new Intent(getApplicationContext(), Home.class);
                    startActivity(HomePge);
                    Toast.makeText(Connect.this, "Connect success", Toast.LENGTH_LONG).show();
                }

                //signInWithPhoneAuthCredential(credential);


            }
        });
    }



    /*private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
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
    }*/

   /* private void updateUI(FirebaseUser currentUser) {
        Intent HomePge = new Intent(getApplicationContext(), Home.class);
        HomePge.putExtra("phone", currentUser.getPhoneNumber());
        Log.v("DATA", currentUser.getUid());
        startActivity(HomePge);

    }*/

}
