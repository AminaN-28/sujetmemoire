package com.example.bloodlineapp.AppDetails;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.bloodlineapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class Profil extends AppCompatActivity {

     TextView occupationTxtView, nameTxtView, agetext;
    private TextView adresstext, phoneTxtView, weightTxtView, bloodtxtview;
    private ImageView userImageView;
    private final String TAG = this.getClass().getName().toUpperCase();
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private Map<String, String> userMap;


    String username, url,userBloodG,userage,userweight,userpassword,useraddress,userphone;
    private static final String USERS = "users";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);


        //receive data from login screen
        //Intent intent = getIntent();

        username = getIntent().getStringExtra("nom");
        userpassword = getIntent().getStringExtra("password");
        useraddress = getIntent().getStringExtra("address");
        userphone = getIntent().getStringExtra("phonenumber");
        userBloodG = getIntent().getStringExtra("groupeS");
        userweight = getIntent().getStringExtra("weight");
        userage = getIntent().getStringExtra("age");
        url = getIntent().getStringExtra("profile");


        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = rootRef.child(USERS);
        Log.v("USERID", userRef.getKey());


        nameTxtView = findViewById(R.id.name_textview);
        agetext = findViewById(R.id.age_textview);
        phoneTxtView = findViewById(R.id.phone_textview);
        adresstext =findViewById(R.id.occupation_textview);
        bloodtxtview = findViewById(R.id.blood_textview);
        weightTxtView = findViewById(R.id.weight);
        userImageView = findViewById(R.id.user_imageview);


        // Read from the database
        userRef.addValueEventListener(new ValueEventListener() {
            String fname, phone, adress, age, blood,img,weight;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot keyId: dataSnapshot.getChildren()) {
                    if (keyId.child("fullName").getValue().equals(username))
                    {
                        fname = keyId.child("fullName").getValue(String.class);
                        adress = keyId.child("address").getValue(String.class);
                        blood = keyId.child("blood").getValue(String.class);
                        age = keyId.child("age").getValue(String.class);
                        weight =keyId.child("weight").getValue(String.class);
                        phone = keyId.child("phone").getValue(String.class);
                        img = keyId.child("url").getValue(String.class);
                        break;
                    }
                }
                nameTxtView.setText(fname);
                bloodtxtview.setText(blood);
                adresstext.setText(adress);
                agetext.setText(age);
                weightTxtView.setText(weight);
                phoneTxtView.setText(phone);
                Picasso.get().load(img).into(userImageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());

            }

        });
    }
    }
