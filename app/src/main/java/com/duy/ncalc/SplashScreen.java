package com.duy.ncalc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.duy.calculator.R;
import com.duy.ncalc.calculator.BasicCalculatorActivity;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import org.jetbrains.annotations.NotNull;

public class SplashScreen extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;
    static final String LOG_TAG = "SplashInterstitial";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        displayAd();
                        startActivity(new Intent(SplashScreen.this,BasicCalculatorActivity.class));
                        finish();
                    }
                },3000);
    }

    private void displayAd(){
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(SplashScreen.this,getString(R.string.admob_inter_start_id),adRequest,
                new InterstitialAdLoadCallback(){
                    @Override
                    public void onAdLoaded(@NonNull @NotNull InterstitialAd interstitialAd) {
                        super.onAdLoaded(interstitialAd);
                        mInterstitialAd = interstitialAd;
                        Log.i(LOG_TAG, "onAdLoaded");
                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when fullscreen content is dismissed.
                                Log.d(LOG_TAG, "The ad was dismissed.");
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when fullscreen content failed to show.
                                Log.d(LOG_TAG, "The ad failed to show.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when fullscreen content is shown.
                                // Make sure to set your reference to null so you don't
                                // show it a second time.
                                mInterstitialAd = null;
                                Log.d(LOG_TAG, "The ad was shown.");
                            }
                        });
                        if (mInterstitialAd != null) {
                            mInterstitialAd.show(SplashScreen.this);
                        } else {
                            Log.d(LOG_TAG, "The interstitial ad wasn't ready yet.");
                        }
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull @NotNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        Log.i(LOG_TAG, loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });

    }
}