package com.example.bloodlineapp.AppDetails;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodlineapp.Login.User;
import com.example.bloodlineapp.Models.Alert;
import com.example.bloodlineapp.Models.AlertAdapter;
import com.example.bloodlineapp.Onboarding.MainActivity;
import com.example.bloodlineapp.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import static com.example.bloodlineapp.Login.Login.ImageUri;

public class Home<mDatabase> extends AppCompatActivity {

    private DrawerLayout mdrawer;

    private  ActionBarDrawerToggle mToggle;

    private NavigationView navDrawer;

    private ChipNavigationBar chipNavigationBar;

    private ScrollView scrollView;

    String token;

    FirebaseAuth mAuth;

    StorageReference mRoot;
    //commentez la liste view apres.
   // ListView listViews;
    //ArrayList<Alert> arrayList = new ArrayList<>();

    private RecyclerView recyclerView;
    private AlertAdapter adapter;

    private FirebaseUser mCurrentUser;//for member variable

    DatabaseReference mGetReference;
    FirebaseDatabase mDatabase ;

    String username, url,userBloodG,userage,userweight,userpassword,useraddress,userphone;
    private static final String USERS = "users";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        mAuth= FirebaseAuth.getInstance();//initialisation of instance
        mCurrentUser = mAuth.getCurrentUser();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mRoot = FirebaseStorage.getInstance().getReference();
      //  mDatabase = database.getReference(USERS);
        //mDBase = FirebaseDatabase.getInstance();
        mDatabase=  FirebaseDatabase.getInstance();
        mGetReference = mDatabase.getReference(USERS);


        username = getIntent().getStringExtra("nom");

        userpassword = getIntent().getStringExtra("password");

        useraddress = getIntent().getStringExtra("address");

        userphone = getIntent().getStringExtra("phonenumber");

        userBloodG = getIntent().getStringExtra("groupeS");

        userweight = getIntent().getStringExtra("weight");

        userage = getIntent().getStringExtra("age");

        url = getIntent().getStringExtra("profile");


        //Add data to firebase database
         String id = mGetReference.push().getKey(); //donner une clé d'identification au user

        saveInFBStorage(id);
        User user = new User( username, useraddress, userphone, userpassword, userBloodG, userweight, userage, url);
        mGetReference.child(id).setValue(user);
        mGetReference.child(mCurrentUser.getUid()).setValue(user);

        mdrawer = findViewById(R.id.drawer);

        navDrawer = findViewById(R.id.drawernav);
        recyclerView = findViewById(R.id.recyclerView);

        chipNavigationBar = findViewById(R.id.bottom_nav);

        mToggle = new ActionBarDrawerToggle(this,mdrawer,R.string.open ,R.string.close);
        mdrawer.addDrawerListener(mToggle);
        mToggle.syncState();




        FirebaseRecyclerOptions<Alert> options =
                new FirebaseRecyclerOptions.Builder<Alert>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("alerts"), Alert.class)
                        .build();

        adapter = new AlertAdapter(options);
        recyclerView.setAdapter(adapter);

        // FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        Handler handler =  new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

              token();

            }
        }, 2000);



        //DrawerLayout item clickable
         navDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                   case R.id.db:
                       Toast.makeText(Home.this,"Dashboared",Toast.LENGTH_LONG).show();
                       Intent firstintent = new Intent(Home.this, Dashboared.class);
                       startActivity(firstintent);
                       return true;

                   case R.id.profil:
                        Toast.makeText(Home.this,"Profil",Toast.LENGTH_LONG).show();
                        Intent secondintent = new Intent(Home.this,Profil.class);
                        startActivity(secondintent);
                        return true;


                   case R.id.event :
                       Toast.makeText(Home.this,"Event",Toast.LENGTH_LONG).show();
                       Intent thirdintent = new Intent(Home.this,Event.class);
                       startActivity(thirdintent);
                       return true;



                   case   R.id.carte:
                       Intent fiveintent = new Intent(Home.this,MapsActivity.class);
                       startActivity(fiveintent);
                       return true;

                  case  R.id.parametres:
                      Toast.makeText(Home.this,"Parametres et Confidentialites",Toast.LENGTH_LONG).show();
//                      Intent sixintent = new Intent(Home.this, Parameters.class);
                      //startActivity(sixintent);
                      return true;

                  case R.id.deconnexion:
                      Toast.makeText(Home.this,"LogOut",Toast.LENGTH_LONG).show();
                      Intent sevenintent = new Intent(Home.this, MainActivity.class);
                      startActivity(sevenintent);
                      finish();
                      return true;
                }
                return true;
            }
         });






        //Make chipNavigation clickable

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id)
            {
                switch (id)
                {

                     case R.id.request:
                         Toast.makeText(Home.this, "Make Request", Toast.LENGTH_LONG).show();
                         Intent chipRequest = new Intent(Home.this, WriteAlert.class);
                         chipRequest.putExtra("nom", username);
                         chipRequest.putExtra("profile", url);
                         startActivity(chipRequest);
                         break;
                     case R.id.notif:
                         Toast.makeText(Home.this, "See Notifications", Toast.LENGTH_LONG).show();
                         Intent chipNotif = new Intent(Home.this, NotificatonAct.class);
                         startActivity(chipNotif);
                         break;
                }

                    }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                        insertTokenTofirebase(user);
                            // Log and toast
                        //  String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("TAG2", "token");
                        Toast.makeText( Home.this, "Home", Toast.LENGTH_SHORT).show();
                    }
                });

    }
   public void insertTokenTofirebase(FirebaseUser user){
        String key = mGetReference.push().getKey();
        mGetReference.child("users").child(user.getUid()).child("token").setValue(token);
        mGetReference.child(key).setValue(user); //adding token info to user tabl

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

    @Override
   protected void onStart() {
       super.onStart();
       adapter.startListening();
   }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}

