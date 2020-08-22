package com.example.bloodlineapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class WriteAlert extends AppCompatActivity {

    EditText text_alert;
    Button publish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_alert);


        text_alert = findViewById(R.id.makerequest);
        publish=findViewById(R.id.publishbtn);


        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Creation d'un dictionnaire
                Map<String,String> map = new HashMap<String, String>();
                map.put("nom","amina");

                // Write a message to the database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("message");
                myRef.setValue("Hello, World!");

                // Read from the database
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.

                        String value = dataSnapshot.getValue(String.class);
                        Log.d("280399", "Value is: " + value);

                    }
                    @Override
                    public void onCancelled(DatabaseError error) {

                        // Failed to read value
                        Log.w("2803","Failed to read value.", error.toException());
                    }
                });

            }
        });

    }
}
