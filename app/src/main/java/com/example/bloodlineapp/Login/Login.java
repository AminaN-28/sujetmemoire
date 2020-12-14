package com.example.bloodlineapp.Login;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bloodlineapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.Locale;


public class Login extends AppCompatActivity {

    private Button next, changelang;//BOUTON POUR ENVOYER AUSSI

    private EditText userName, userPhone, city, bloodg, userpassword, userage, userweight;

    private ImageView profile;

    private String fname, phone, address, password, bloodG, age, weight, userprofile;

    private TextView logindirect;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private User user;

    private static final String USERS = "users";

    private FirebaseAuth mAuth; //Create an instance of FirebaseAuth


    private FirebaseDatabase mydatabase; // Instance Base de donnée

    private DatabaseReference mDatabase; //reference

    private final int REQUEST_CODE = 100;

    public static Uri ImageUri;
    //Alert alert;

    StorageReference storageReference;
    private StorageTask uploadTask;

    Bitmap bitmap;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = new User();
        storageReference= FirebaseStorage.getInstance().getReference("UsersProfiles");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("alerts");



       /* changelang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Show AlertDialog to display list of languages , one only can be selected
                showChangeLangageDialog();

            }
        });*/


       // ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle(getResources().getString(R.string.app_name));


        next = findViewById(R.id.button);
        profile =findViewById(R.id.imageView);
        userName = findViewById(R.id.username);
        userPhone = findViewById(R.id.userPhone);
        bloodg = findViewById(R.id.bloodgroup);
        city = findViewById(R.id.usercity);
        userpassword = findViewById(R.id.userpassword);
        logindirect = findViewById(R.id.logindirct);
        userage = findViewById(R.id.userage);
        userweight = findViewById(R.id.userweight);
        mydatabase = FirebaseDatabase.getInstance();
        mDatabase = mydatabase.getReference(USERS);
        mAuth = FirebaseAuth.getInstance();

        addProfilePic();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //insert data into firebase database
                if (validateFullName() | validatePassword() | validateAddress() | validatePhone()
                        | validateBlood() | validateAge() | validateWeight() | ImageUri != null) {
                    fname = userName.getText().toString();
                    address = city.getText().toString();
                    password = userpassword.getText().toString();
                    bloodG = bloodg.getText().toString();
                    age = userage.getText().toString();
                    weight = userweight.getText().toString();
                    phone = userPhone.getText().toString();//convertir le numero en string
                    //userprofile =profile.getResources().toString();
                   Intent logInt = new Intent(Login.this, OTPVerify.class);
                   logInt.putExtra("nom", userName.getText().toString());
                   logInt.putExtra("phonenumber", userPhone.getText().toString());
                   logInt.putExtra("address", city.getText().toString());
                   logInt.putExtra("password", userpassword.getText().toString());
                   logInt.putExtra("groupeS", bloodg.getText().toString());
                   logInt.putExtra("age", userage.getText().toString());
                   logInt.putExtra("weight", userweight.getText().toString());
                   logInt.putExtra("profile", userprofile);
                    //UploadToFireBase();

                    //logInt.putExtra("string",verificationId);
                    // logInt.putExtra("forcing",token);

                    startActivity(logInt);
                    finish();

                }
            }
        });

    }



    //validate function
    // validation functions
    private boolean validateFullName() {
        String val = userName.getText().toString().trim();
        if (val.isEmpty()) {
            userName.setError("Field  can not be empty");
            return false;
        } else {
            userName.setError(null);
            return true;
        }
    }

    private boolean validateBlood() {
        String val = bloodg.getText().toString().trim();
        String checkbloodg = "A-" + "A+" + "B+" + "B-" + "AB+" + "AB-" + "O+" + "O-"; // all blood group

        if (val.isEmpty()) {
            bloodg.setError("Field  can not be empty");
            return false;
        } else if (!val.matches(checkbloodg)) {
            userpassword.setError("Password should contain 8 characters! ");
            return false;
        } else {
            bloodg.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        /*return true;*/
        String val = userpassword.getText().toString().trim();
        String checkpassword = "^" +
                "(?=.*[a-zA-Z])" + //any letter
                "(?=.*[@#$^&+=])" + //at least 1 special caracter
                "(?=\\S+$)" + // no white spaces
                ".{8,}" +   // at least 8 characters
                "$";
        if (val.isEmpty()) {
            userpassword.setError("Field  can not be empty");
            return false;
        } else if (!val.matches(checkpassword)) {
            userpassword.setError("Password should contain 8 characters! ");
            return false;
        } else {
            userpassword.setError(null);
            return true;
        }
    }

    private boolean validatePhone() {
        String val = userPhone.getText().toString().trim();
        if (val.isEmpty()) {
            userPhone.setError("Field  can not be empty");
            return false;
        } else {
            userPhone.setError(null);
            return true;
        }
    }

    private boolean validateAddress() {
        String val = city.getText().toString().trim();
        if (val.isEmpty()) {
            city.setError("Field  can not be empty");
            return false;
        } else {
            city.setError(null);
            return true;
        }
    }

    private boolean validateAge() {
        String val = userage.getText().toString().trim();
        if (val.isEmpty()) {
            userage.setError("Field can not be empty");
            return false;
        } else {
            userage.setError(null);
            return true;
        }

    }

    private boolean validateWeight() {
        String val = userweight.getText().toString().trim();
        if (val.isEmpty()){
            userweight.setError("Field can not be empty");
            return false;
        }
        else
        {
            userweight.setError(null);
            return true;
        }
    }

    public void addProfilePic(){

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseimg = new Intent(  );
                chooseimg.setType("image/*");
                chooseimg.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(chooseimg , REQUEST_CODE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK  && data != null && data.getData()!= null){
            ImageUri = data.getData();
            profile.setImageURI(ImageUri);
          /*  try
            {
                InputStream inputStream=getContentResolver().openInputStream(ImageUri);
                bitmap= BitmapFactory.decodeStream(inputStream);
                profile.setImageBitmap(bitmap);
            }
            catch (Exception e)
            {

            }*/
        }

    }
    /*private void UploadToFireBase(){
        String imageid;
        imageid =System.currentTimeMillis()+"."+getExtension(ImageUri);
        user.setFullName(userName.getText().toString().trim());
        user.setAddress(city.getText().toString().trim());
        user.setWeight(userweight.getText().toString().trim());
        user.setPhone(userPhone.getText().toString().trim());
        user.setAge(userage.getText().toString().trim());
        user.setPassword(userpassword.getText().toString().trim());
        user.setBlood(bloodg.getText().toString().trim());
        user.setUrl(imageid);
        //mDatabase.push().setValue(user);

        StorageReference reference = storageReference.child(imageid);
        reference.putFile(ImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                       // Uri downloadUrl = taskSnapshot.getDownloadUrl();

                        Toast.makeText(Login.this, "Image Successfully Update", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }

    private String getExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
*/

    private void showChangeLangageDialog() {
        final String [] list = {"French", "Català", "جزائري", "موريتاني", "English Us"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Login.this);
        mBuilder.setTitle("Choose Language.....");
        mBuilder.setSingleChoiceItems(list, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0){
                    setLocale("fr");
                    recreate();
                }
                else if (which == 1){
                    setLocale("ca");
                    recreate();
                }
                else if (which == 2){
                    setLocale("ar");
                    recreate();
                }
                else if (which == 3){
                    setLocale("ar");
                    recreate();
                }
                else
                {
                    setLocale("en");
                    recreate();
                }

                //dismiss alertwhen item selected

                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = mBuilder.create();

        alertDialog.show();

    }

    private void setLocale(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration configuration = new Configuration();
        configuration.locale =locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());

        //saved data to shared preferences
        SharedPreferences.Editor editor =  getSharedPreferences("Settinngs", MODE_PRIVATE).edit();
        editor.putString("My Language", language);
        editor.apply();
    }

    public void loadLocale(){
        SharedPreferences preferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String lang = preferences.getString("My Language","");
        setLocale(lang);
    }



}
