package com.example.bloodlineapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

public class Home extends AppCompatActivity {

    private DrawerLayout mdrawer;
    private  ActionBarDrawerToggle mToggle;
    SpaceNavigationView navigationView;
    private NavigationView navDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mdrawer = findViewById(R.id.drawer);
        navDrawer = findViewById(R.id.drawernav);
        mToggle = new ActionBarDrawerToggle(this,mdrawer,R.string.open ,R.string.close);
        mdrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //FloatingActionButon

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent fab_int =new Intent(Home.this,WriteAlert.class);
                startActivity(fab_int);
            }
        });

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


                    case  R.id.signet :
                        Toast.makeText(Home.this,"Signet",Toast.LENGTH_LONG).show();
                        Intent fourintent = new Intent(Home.this,Signet.class);
                        startActivity(fourintent);
                        return true;


                        case   R.id.carte:
                        Intent fiveintent = new Intent(Home.this,MapsActivity.class);
                        startActivity(fiveintent);
                        return true;

                    case  R.id.parametres:
                        Toast.makeText(Home.this,"Parametres et Confidentialites",Toast.LENGTH_LONG).show();
                        Intent sixintent = new Intent(Home.this, Parameter.class);
                        startActivity(sixintent);
                        return true;


                    case R.id.deconnexion:
                        Toast.makeText(Home.this,"Deconnexion",Toast.LENGTH_LONG).show();
                        Intent sevenintent = new Intent(Home.this,MainActivity.class);
                        startActivity(sevenintent);
                        return true;
                }
                return true;
            }
        });


        navigationView = findViewById(R.id.space);

        navigationView.initWithSaveInstanceState(savedInstanceState);
        navigationView.initWithSaveInstanceState(savedInstanceState);
        navigationView.addSpaceItem(new SpaceItem("SEARCH", R.drawable.ic_search_black_24dp));
        navigationView.addSpaceItem(new SpaceItem("NOTIFICATION", R.drawable.ic_notifications_black_24dp));

        navigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                Toast.makeText(Home.this,"HOME", Toast.LENGTH_SHORT).show();
                navigationView.setCentreButtonSelectable(true);
            }
            @Override
            public void onItemClick(int itemIndex, String itemName) {
                Toast.makeText(Home.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
                Intent int_search= new Intent(Home.this,SearchAct.class);
                startActivity(int_search);
            }
            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                Toast.makeText(Home.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
                Intent int_notif= new Intent(Home.this,NotificatonAct.class);
                startActivity(int_notif);

            }
        });



        //RECUPERER LE TEXTE PUBLIE ,LA DATE DE PUBLICATION ET LE NUMERO DU USER

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
