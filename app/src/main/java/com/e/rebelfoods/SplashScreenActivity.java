package com.e.rebelfoods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBar(null);
        setContentView(R.layout.activity_splash_screen);

        final TextView rebelFoodsText = findViewById(R.id.rebel_foods);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Glegoo-Bold.ttf");
        rebelFoodsText.setTypeface(typeface);

        final TextView userText = findViewById(R.id.desc);
        userText.setTypeface(typeface);
        Animation titleAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce_title);
        Animation descAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_desc);
        rebelFoodsText.setAnimation(titleAnimation);
        userText.setAnimation(descAnimation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                finish();

            }
        }, 3000);
    }
}
