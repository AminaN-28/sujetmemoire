package com.example.bloodlineapp.AppDetails;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.bloodlineapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.installations.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends AppCompatActivity {
    //Initialize variable
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //Assign fragment
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_maps);

        //Initialize fused location
        client = LocationServices.getFusedLocationProviderClient(this);

        //Check permission
        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //When permission
            //Call Method
            getCurrentLocation();
        }else{
            //When permission denied
            //Request permission
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    private void getCurrentLocation() {
        //Initialize task location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                if (location != null)
                {
                    //sync map
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            //Initialize Lat Long
                            LatLng latLng= new LatLng(location.getLatitude(),
                                    location.getLongitude());
                            //Centre Hopitalier Universitaire de fann
                            LatLng latLng1 =new LatLng(14.695855388835863, -17.4642918455977);
                            LatLng latLng3 = new LatLng(14.695911153153789, -17.465614837769177);
                            //Hopital Principal
                            LatLng latLng5 = new LatLng(14.663210588083288, -17.435030969197964);
                            //Centre Hospitalier Abass Ndao
                            LatLng latLng6 = new LatLng(14.685946926714227, -17.454199534220205);
                            //Centre Hospitalier de Ouakam
                            LatLng latLng7 = new LatLng(14.716121030643633, -17.482006712817224);
                            //Hôpital Général IDRISSA POUYE de Grand Yoff Sénégal
                            LatLng latLng8 = new LatLng(14.733649452181828, -17.44587830169171);
                            //Hopital Aristide LE DANTEC
                            LatLng latLng9 = new LatLng(14.657556011689197, -17.436700109949978);
                            //Centre Hospitalier de l'Ordre de Malte : CHOM
                            LatLng latLng10 = new LatLng(14.690487192609895, -17.465792489851687);


                            //Create a marker options
                            MarkerOptions options = new MarkerOptions().position(latLng).title("Donneur").visible(true);
                            MarkerOptions options1 = new MarkerOptions().position(latLng1).title("Centre Hospitalier de Fann").visible(true);
                            MarkerOptions options2 =new MarkerOptions().position(latLng3).title("Centre national de transfusion sanguine CNTS").visible(true);
                           MarkerOptions options3 = new MarkerOptions().position(latLng5).title("Hopital Principal de Dakar").visible(true);
                           MarkerOptions options4 = new MarkerOptions().position(latLng6).title("Centre Hospitalier Abass Ndao").visible(true);
                            MarkerOptions options5 = new MarkerOptions().position(latLng7).title("Hopital Militaire de Ouakam").visible(true);
                            MarkerOptions options6 = new MarkerOptions().position(latLng8).title("Hôpital Général IDRISSA POUYE de Grand Yoff Sénégal").visible(true);
                            MarkerOptions options7 = new MarkerOptions().position(latLng9).title("Hopital Aristide LE DANTEC").visible(true);
                            MarkerOptions options8 = new MarkerOptions().position(latLng10).title("Centre Hospitalier de l'Ordre de Malte : CHOM").visible(true);

                            //Zoom map
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                            //Add marker on map
                            googleMap.addMarker(options);
                            googleMap.addMarker(options1).setTitle("Centre Hospitalier de Fann");
                            googleMap.addMarker(options2).setTitle("Centre national de transfusion sanguine CNTS");
                            googleMap.addMarker(options3).setTitle("Hopital Principal de Dakar");
                            googleMap.addMarker(options4).setTitle("Centre Hospitalier Abass Ndao");
                            googleMap.addMarker(options5).setTitle("Hopital Militaire de Ouakam");
                            googleMap.addMarker(options6).setTitle("Hôpital Général IDRISSA POUYE de Grand Yoff Sénégal");
                            googleMap.addMarker(options7).setTitle("Hopital Aristide LE DANTEC");
                            googleMap.addMarker(options8).setTitle("Centre Hospitalier de l'Ordre de Malte : CHOM");



                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 44 )
        { if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            //When permisson granted
            // call method
            getCurrentLocation();
        }

        }



    }


}
