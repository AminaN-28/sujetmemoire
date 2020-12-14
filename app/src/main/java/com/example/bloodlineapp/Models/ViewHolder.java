package com.example.bloodlineapp.Models;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bloodlineapp.AppDetails.Home;
import com.example.bloodlineapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewHolder extends RecyclerView.ViewHolder {

    CardView cardView;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
    }
    public static String USERS = "users";

    public void setDetails(Context context,String image, String fullname ,String alert,String bloodneeded ,String date)
    {
        final CircleImageView imageView = itemView.findViewById(R.id.img1);
        TextView textView = itemView.findViewById(R.id.nametext);
        TextView textView1 = itemView.findViewById(R.id.alerttext);
        TextView textView2 = itemView.findViewById(R.id.bloodtext);
        TextView textView3 =itemView.findViewById(R.id.datetext);

     //  Picasso.get().load(image).into(imageView);
        textView.setText(fullname);
        textView1.setText(alert);
        textView3.setText(date);
        textView2.setText(bloodneeded);

        StorageReference mRoot = FirebaseStorage.getInstance().getReference("UsersProfiles");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("url"))
                {
                    String link = snapshot.getValue().toString();
                    Picasso.get().load(link).into(imageView);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
//        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.overshoot_interpolator);

        itemView.setAnimation(animation);
    }
}
