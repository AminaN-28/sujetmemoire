 package com.example.bloodlineapp.AppDetails;

 import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
 import android.util.Log;
 import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.bloodlineapp.Models.Alert;
import com.example.bloodlineapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.loopj.android.http.AsyncHttpClient;
 import com.loopj.android.http.JsonHttpResponseHandler;
 import com.loopj.android.http.RequestParams;

 import org.json.JSONObject;

 import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

 import cz.msebera.android.httpclient.Header;

 public class WriteAlert extends AppCompatActivity {

    EditText text_alert;
    Button publish;
    CardView cardView;
    String username, url,userBloodG,userage,userweight,userpassword,useraddress,userphone;


   int [] tokenarray ;
    private  static  final String Base_url ="https://fcm.googleapis.com/v1/projects/bloodlineapp-f26f1/messages:send";
   private static AsyncHttpClient client = new  AsyncHttpClient();


    private static final String ALERTS = "alerts";

    private static final String USERS = "users";

    String alert , date, blood,fullName,profile;

    private FirebaseAuth mAuth; //Create an instance of FirebaseAuth

    //our database reference object
    DatabaseReference database;
    Spinner bloodg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_alert);

        username = getIntent().getStringExtra("nom");

        userpassword = getIntent().getStringExtra("password");

        useraddress = getIntent().getStringExtra("address");

        userphone = getIntent().getStringExtra("phonenumber");

        userBloodG = getIntent().getStringExtra("groupeS");

        userweight = getIntent().getStringExtra("weight");

        userage = getIntent().getStringExtra("age");

        url = getIntent().getStringExtra("profile");

        text_alert = findViewById(R.id.makerequest);

        publish=findViewById(R.id.publishbtn);

        mAuth = FirebaseAuth.getInstance();






        //Spinner blood group
        bloodg = (Spinner) findViewById(R.id.spinner_blood);
        List<String> spinneradd = new ArrayList<>();
        spinneradd.add("A+");
        spinneradd.add("A-");
        spinneradd.add("B+");
        spinneradd.add("B-");
        spinneradd.add("O+");
        spinneradd.add("O-");
        spinneradd.add("AB+");
        spinneradd.add("AB-");




        //Creating adapter for spinner
        ArrayAdapter<String> bloodAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, spinneradd);

        // attaching data adapter to spinner
        bloodg.setAdapter(bloodAdapter);

        //getting the reference of alerts node
        database =FirebaseDatabase.getInstance().getReference(ALERTS);

        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //insert data into firebase database
                //calling the method addAlert()
                //the method is defined below
                //this method is actually performing the write operation

                addAlert();

            }
        });
    }
    /*
     * This method is saving a new alert to the
     * Firebase Realtime Database
     * */
    private void addAlert() {

        //checking if the value is provided
        if (text_alert  != null)
        {
            alert = text_alert.getText().toString().trim();
            blood = bloodg.getSelectedItem().toString().trim();

          Calendar c = Calendar.getInstance();
          SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");

          //  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss.SSS' '");

            df.setTimeZone(TimeZone.getTimeZone("GMT"));
            try {
                long time = df.parse("2016-01-24T16:00Z").getTime();
                long now = System.currentTimeMillis();
                CharSequence ago =
                        DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String formattedDate = df.format(c.getTime());

          //  String ago = p.format(new Date(formattedDate));
            date = formattedDate.trim();

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Alert
            final String id = database.push().getKey();

            //creating an Alert Object
            Alert alertposted = new Alert(alert,url,username,blood,date);
            //Saving the Alert
            database.child(id).setValue(alertposted);

            //setting edittext to blank again
            //  text_alert.setText("");


        //Read alert to Database
            DatabaseReference data = FirebaseDatabase.getInstance().getReference();
            final DatabaseReference alertRef = data.child(ALERTS).child(mAuth.getUid());
            alertRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String getdata = (String) snapshot.getValue();

                    //displaying a success toast
                    //Toast.makeText(WriteAlert.this, getdata, Toast.LENGTH_LONG).show();
                    //notification();

                    Intent publish = new Intent(WriteAlert.this, Home.class);
                    publish.putExtra("nom", username);
                    publish.putExtra("profile",url);
                    publish.putExtra("address",useraddress);
                    publish.putExtra("password",userpassword);
                    publish.putExtra("groupeS",userBloodG);
                    publish.putExtra("weight",userweight);
                    publish.putExtra("age",userage);
                    publish.putExtra("phonenumber",userphone);
                    //Read Data from Data Base
                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                    final DatabaseReference userRef = rootRef.child(USERS);
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot)
                        {
                            for(DataSnapshot ds : snapshot.getChildren()) {
                                String tokenretrieve = String.valueOf(ds.child("token"));

                                // Toast.makeText(WriteAlert.this, tokenretrieve, Toast.LENGTH_LONG).show();

                                sendNotif(tokenretrieve);
                                Log.d("rrr", "OKKKK NOTIFICATION");

                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error)
                        {

                        }
                    });
                    // Log.v("ALERTID", alertRef.getKey());

                    startActivity(publish);
                    finish();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        else {
            //if the value is not given displaying a toast
               // Toast.makeText(this, "Please enter an alert", Toast.LENGTH_LONG).show();
                text_alert.setError("Faites une publication");

        }
    }

    public void sendNotif(String A){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("to",A);
        String Url = "https://fcm.googleapis.com/fcm/send";

        String serverToken = "AAAAPzxCX-s:APA91bFjcnPvoJaB1u4D6ZTkLjTSOBzxr6YVRk94r5HLRFe0PZK9wgD3dLS1Eu-XtiCxheHM8Be1OEVe8PMArDaWM8CJh5FU6-8asISB_6W3Dr0MckR58dx_Ps0WYC84VQYt4l9oz5ER";
        client.addHeader("Authorization", "key="+serverToken);
        client.addHeader("Content-Type", "application/json");

        HashMap<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("title", "URGENT!");
        paramMap.put("text", "alert");
        paramMap.put("click_action", "Home");
        params.put("notification", paramMap);


        HashMap<String, String> secondParam = new HashMap<String, String>();
        secondParam.put("keyname", "username");
        params.put("data",secondParam);


        client.post(Url, params, new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("280399", ""+errorResponse);
                Log.d("290520", ""+throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
               // super.onSuccess(statusCode, headers, response);

                Log.d("2803","" +response);
            }
        });

    }
}
