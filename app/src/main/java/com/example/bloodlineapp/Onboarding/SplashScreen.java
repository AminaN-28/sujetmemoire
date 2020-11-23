package com.example.bloodlineapp.Onboarding;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.bloodlineapp.AppDetails.Home;
import com.example.bloodlineapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {
    ImageView ivTop, ivBottom, ivSplash, icline;
    TextView textView;
    CharSequence charSequence;
    int index;
    FirebaseAuth mCurrentuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        mCurrentuser  = FirebaseAuth.getInstance();



        textView =findViewById(R.id.textmiddle);
        ivBottom=findViewById(R.id.bottom);
        ivTop = findViewById(R.id.topimg);
        ivSplash = findViewById(R.id.img);
        icline = findViewById(R.id.beat);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        ivTop.setAnimation(animation1);

        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/demoapp-ae96a.appspot.com/o/heart_beat.gif?alt=media&token=b21dddd8-782c-457c-babd-f2e922ba172b")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(icline);

        Animation animation2 =AnimationUtils.loadAnimation(this, R.anim.bottom_anim);
        ivBottom.setAnimation(animation2);



        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(
               ivSplash,
               PropertyValuesHolder.ofFloat("scaleX", (float) 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", (float) 1.2f)
                );
        objectAnimator.setDuration(5000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.start();


        Handler handler =  new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(mCurrentuser != null)
                {
                    Intent login = new Intent(SplashScreen.this, Home.class);
                    startActivity(login);

                }
                else
                {
                    Intent sign = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(sign);
                }


            }
        }, 2000);
    }

}