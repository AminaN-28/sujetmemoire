package com.example.bloodlineapp.AppDetails;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodlineapp.Models.Alert;
import com.example.bloodlineapp.Models.AlertAdapter;
import com.example.bloodlineapp.Models.ViewHolder;
import com.example.bloodlineapp.Onboarding.MainActivity;
import com.example.bloodlineapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.StorageReference;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class Home<mDatabase> extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private Menu menu;

    private NavigationView navDrawer;

    private ChipNavigationBar chipNavigationBar;


    String token;

    FirebaseAuth mAuth;

    StorageReference mRoot;

    private RecyclerView recyclerView;
    private AlertAdapter adapter;

    private FirebaseUser mCurrentUser;//for member variable

    DatabaseReference mGetReference ,alertGetref;
    FirebaseDatabase mDatabase ;

    String username, url,userBloodG,userage,userweight,userpassword,useraddress,userphone;
    private static final String USERS = "users";
    private static final String ALERTS = "alerts";


    //private RequestQueue mRequestQue;



    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        mAuth= FirebaseAuth.getInstance();//initialisation of instance
        mCurrentUser = mAuth.getCurrentUser();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        mDatabase=  FirebaseDatabase.getInstance();

        mGetReference = mDatabase.getReference(USERS);

        username = getIntent().getStringExtra("nom");

        userpassword = getIntent().getStringExtra("password");

        useraddress = getIntent().getStringExtra("address");

        userphone = getIntent().getStringExtra("phonenumber");

        userBloodG = getIntent().getStringExtra("groupeS");

        userweight = getIntent().getStringExtra("weight");

        userage = getIntent().getStringExtra("age");

       // url = getIntent().getStringExtra("profile");

        //Add data to firebase database
         String id = mGetReference.push().getKey(); //donner une clé d'identification au user



//Canal de notification
       // notification();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        alertGetref = database.getReference(ALERTS);

        chipNavigationBar = findViewById(R.id.bottom_nav);


        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle;
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(R.color.colorAccent);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        // FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        Handler handler =  new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
              token();
            }
        }, 2000);


        //Make chipNavigation clickable

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id)
            {
                switch (id)
                {

                     case R.id.request:
                        // Toast.makeText(Home.this, "Make Request", Toast.LENGTH_LONG).show();
                         Intent chipRequest = new Intent(Home.this, WriteAlert.class);
                         chipRequest.putExtra("nom", username);
                         chipRequest.putExtra("address",useraddress);
                         chipRequest.putExtra("password",userpassword);
                         chipRequest.putExtra("groupeS",userBloodG);
                         chipRequest.putExtra("weight",userweight);
                         chipRequest.putExtra("age",userage);
                         chipRequest.putExtra("phonenumber",userphone);
                         startActivity(chipRequest);
                         break;
                     /*case R.id.notif:
                         Toast.makeText(Home.this, "See Notifications", Toast.LENGTH_LONG).show();
                         //Intent chipNotif = new Intent(Home.this, NotificatonAct.class);
                         //startActivity(chipNotif);
                         break;*/
                }
            }
        });



        
    }



    public void token(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG1", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        token = task.getResult();
                       FirebaseUser user = mAuth.getCurrentUser();
                        //User users = new User( username, useraddress, userphone, userpassword, userBloodG, userweight, userage, userprofile);
                      insertTokenTofirebase(user);
                            // Log and toast
                        //  String msg = getString(R.string.msg_token_fmt, token);
                        
                        Log.d("TAG2", "token");
                       // Toast.makeText( Home.this, "Home", Toast.LENGTH_SHORT).show();
                    }
                });

    }
  public void insertTokenTofirebase(FirebaseUser user){
        String key = mGetReference.push().getKey();
       mGetReference.child(user.getUid()).child("token").setValue(token);
      // mGetReference.child(key).setValue(user); //adding token info to user table

    }

    @Override
   protected void onStart() {
       super.onStart();

       FirebaseRecyclerOptions<Alert> option2 =
               new FirebaseRecyclerOptions.Builder<Alert>()
               .setQuery(alertGetref , Alert.class)
               .build();

        FirebaseRecyclerAdapter<Alert , ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Alert, ViewHolder>(option2) {
                    @Override
                    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Alert model) {

                        holder.setDetails(getApplicationContext(), model.getUrl(), model.getFullname(), model.getAlert() , model.getBloodneeded(), model.getDatealert());
                        String fullname = getItem(position).getFullname();
                        String alert = getItem(position).getAlert();
                        String date = getItem(position).getDatealert();
                        String blood = getItem(position).getBloodneeded();
                        String url = getItem(position).getUrl();

                    }

                    @NonNull
                    @Override
                    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.singlerow, parent,false);

                        return new ViewHolder(view);
                    }
                };
            firebaseRecyclerAdapter.startListening();
            recyclerView.setAdapter(firebaseRecyclerAdapter);

   }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){

            case R.id.profil:
                //Toast.makeText(Home.this,"Profil",Toast.LENGTH_LONG).show();
                Intent secondintent = new Intent(Home.this,Profil.class);
                secondintent.putExtra("nom", username);
                secondintent.putExtra("address",useraddress);
                secondintent.putExtra("password",userpassword);
                secondintent.putExtra("groupeS",userBloodG);
                secondintent.putExtra("weight",userweight);
                secondintent.putExtra("age",userage);
                secondintent.putExtra("phonenumber",userphone);
                startActivity(secondintent);
                return true;


            case R.id.event :
                //Toast.makeText(Home.this,"Event",Toast.LENGTH_LONG).show();
                Intent thirdintent = new Intent(Home.this,Event.class);
                startActivity(thirdintent);
                return true;



            case   R.id.carte:
                Intent fiveintent = new Intent(Home.this,MapsActivity.class);
                startActivity(fiveintent);
                return true;


            case R.id.deconnexion:
                //Toast.makeText(Home.this,"LogOut",Toast.LENGTH_LONG).show();
                Intent sevenintent = new Intent(Home.this, MainActivity.class);
                startActivity(sevenintent);
                finish();
                return true;

        }
        return true;
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}

