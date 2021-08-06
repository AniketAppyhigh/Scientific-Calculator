package com.duy.ncalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.duy.calculator.R;
import com.duy.ncalc.calculator.BasicCalculatorActivity;
import com.google.android.gms.ads.InterstitialAd;

public class SplashScreen extends AppCompatActivity {

    InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        AdManager adManager = new AdManager(this);
        interstitialAd = adManager.getInterstitialAd();
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        interstitialAd.show();
                    }
                },0);
                startActivity(new Intent(SplashScreen.this, BasicCalculatorActivity.class));
                finish();
            }
        },3000);
    }
}