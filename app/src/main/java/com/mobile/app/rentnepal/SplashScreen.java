package com.mobile.app.rentnepal;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class SplashScreen extends AppCompatActivity {
    ImageView view;
    TextView view1, view2;
    SharedPreferences pref;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        view = (ImageView) findViewById(R.id.imageView);
        view1 = (TextView) findViewById(R.id.textView);
        view2 = (TextView) findViewById(R.id.textView2);
        Animation anim = AnimationUtils.loadAnimation(getBaseContext(), R.anim.slider);
        view.setAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view1.setVisibility(View.VISIBLE);
                view2.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.Landing).duration(700).playOn(view1);
                YoYo.with(Techniques.Landing).duration(700).playOn(view2);
                new CountDownTimer(2000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        try {
                            pref = getSharedPreferences("user", MODE_PRIVATE);
                            String user = pref.getString("UserId", "Null");
                            if (user.equals("Null")) {
                                SplashScreen.this.finish();
                                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                            } else {
                                SplashScreen.this.finish();
                                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                            }
                        } catch (Exception err) {
                        }
                    }
                }.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}
