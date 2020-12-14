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
import com.google.android.gms.maps.model.CircleOptions;
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
                            //Dalal Jamm
                            LatLng latLng2 = new LatLng(14.773770530727372, -17.40901978408021);
                            //Youssou Mbergane
                            LatLng latLng4 = new LatLng(14.729978808719729, -17.260208999076053);
                            //Hôpital Régional El Hadji Ahmadou Sakhir NDIEGUENE de Thiès
                            LatLng latLng11 = new LatLng(14.785952700495624, -16.922190965971502);
                            //
                            LatLng latLng12 = new LatLng(14.65027, -16.23283);
                            //Hopital Ndiaye Touba Djanatu
                            LatLng latLng13 = new LatLng(14.854173723396174, -15.921007231772364);
                            //Hôpital régional Ahmadou Sakhir Mbaye de Louga
                            LatLng latLng14 = new LatLng(15.616317418711741, -16.235977490358223);
                            //Centre Hospitalier Régional de Saint-Louis
                            LatLng latLng15 = new LatLng(16.02313342949805, -16.50593343268101);
                            //Hopital Régional de Matam
                            LatLng latLng16 = new LatLng(15.660078478514906, -13.261730917347757);
                            //Centre Hospitalier RéGional De Fatick
                            LatLng latLng17 = new LatLng(14.34615498273657, -16.414262333426148);
                            //Centre hospitalier régional EIN
                            LatLng latLng18 = new LatLng(14.14214168439713, -16.074564458826785);
                            //Hopital El Hadji Ibrahima Niass
                            LatLng latLng19 = new LatLng(14.167659980854364, -16.076056827088674);
                            //HOPITAL REGIONAL DE KOLDA
                            LatLng latLng20 = new LatLng(12.919427523068993, -14.953413569901668);
                            //Hôpital régional Tambacounda
                            LatLng latLng21 = new LatLng(13.753664199436473, -13.67178466155438);
                            //Hôpital Régional de Ziguinchor
                            LatLng latLng22 = new LatLng(12.55983132884657, -16.282588655361106);
                            //Hopital de la Paix
                            LatLng latLng23 = new LatLng(12.570805843866113, -16.27615135438983);
                            //Hôpital régional de Kaffrine
                            LatLng latLng24 = new LatLng(14.10000880114826, -15.556619077626182);
                            //Hôpital Régional de Sedhiou
                            LatLng latLng25 = new LatLng(12.714434977709708, -15.553124685790417);



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
                            MarkerOptions options9 = new MarkerOptions().position(latLng2).title("Hopital Dalal Jamm").visible(true);
                            MarkerOptions options10 = new MarkerOptions().position(latLng4).title("Youssou Mbergane").visible(true);
                            MarkerOptions options11 = new MarkerOptions().position(latLng11).title("Hôpital Régional El Hadji Ahmadou Sakhir NDIEGUENE de Thiès");
                            MarkerOptions options12 = new MarkerOptions().position(latLng12).title("Hopital Régional de Diourbel Heinrich Lübke").visible(true);
                            MarkerOptions options13 = new MarkerOptions().position(latLng13).visible(true).title("Hopital Ndiaye Touba Djanatu");
                            MarkerOptions options14 = new MarkerOptions().position(latLng14).title("Hôpital régional Ahmadou Sakhir Mbaye de Louga").visible(true);
                            MarkerOptions options15 = new MarkerOptions().position(latLng15).title("Centre Hospitalier Régional de Saint-Louis").visible(true);
                            MarkerOptions options16 = new MarkerOptions().position(latLng16).title("Hopital Régional de Matam").visible(true);
                            MarkerOptions options17 = new MarkerOptions().position(latLng17).title("Centre Hospitalier RéGional De Fatick").visible(true);
                            MarkerOptions options18 = new MarkerOptions().position(latLng18).title("Centre hospitalier régional EIN").visible(true);
                            MarkerOptions options19 = new MarkerOptions().position(latLng19).title("Hopital El Hadji Ibrahima Niass").visible(true);
                            MarkerOptions options20 = new MarkerOptions().position(latLng20).title("Hopital Regional de Kolda").visible(true);
                            MarkerOptions options21 = new MarkerOptions().position(latLng21).title("Hôpital régional Tambacounda").visible(true);
                            MarkerOptions options22 = new MarkerOptions().position(latLng22).title("Hôpital Régional de Ziguinchor").visible(true);
                            MarkerOptions options23 = new MarkerOptions().position(latLng23).title("Hopital de la Paix de Ziguinchor").visible(true);
                            MarkerOptions options24 = new MarkerOptions().position(latLng24).title("Hôpital régional de Kaffrine").visible(true);
                            MarkerOptions options25 = new MarkerOptions().position(latLng25).title("Hôpital Régional de Sedhiou").visible(true);


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
                            googleMap.addMarker(options9).setTitle("Hopital Dalal Jamm");
                            googleMap.addMarker(options10).setTitle("Youssou Mbergane");
                            googleMap.addMarker(options11).setTitle("Hôpital Régional El Hadji Ahmadou Sakhir NDIEGUENE de Thiès");
                            googleMap.addMarker(options12).setTitle("Hopital Régional de Diourbel Heinrich Lübke");
                            googleMap.addMarker(options13).setTitle("Hopital Ndiaye Touba Djanatu");
                            googleMap.addMarker(options14).setTitle("Hôpital régional Ahmadou Sakhir Mbaye de Louga");
                            googleMap.addMarker(options15).setTitle("Centre Hospitalier Régional de Saint-Louis");
                            googleMap.addMarker(options16).setTitle("Hopital Régional de Matam");
                            googleMap.addMarker(options17).setTitle("Centre Hospitalier RéGional De Fatick");
                            googleMap.addMarker(options18).setTitle("Centre hospitalier régional EIN");
                            googleMap.addMarker(options19).setTitle("Hopital El Hadji Ibrahima Niass");
                            googleMap.addMarker(options20).setTitle("Hopital Regional de Kolda");
                            googleMap.addMarker(options21).setTitle("Hôpital Régional de Tambacounda");
                            googleMap.addMarker(options22).setTitle("Hôpital Régional de Ziguinchor");
                            googleMap.addMarker(options23).setTitle("Hopital de la Paix de Ziguinchor");
                            googleMap.addMarker(options24).setTitle("Hôpital régional de Kaffrine");
                            googleMap.addMarker(options25).setTitle("Hôpital Régional de Sedhiou");



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
