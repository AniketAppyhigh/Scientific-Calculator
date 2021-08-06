package com.duy.ncalc;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.duy.calculator.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

import java.util.Date;

import static androidx.lifecycle.Lifecycle.Event.ON_START;

public class AppOpenAdManager implements LifecycleObserver, Application.ActivityLifecycleCallbacks {

    private AppOpenAd appOpenAd=null;
    private AppOpenAd.AppOpenAdLoadCallback loadCallback;
    private CalcApplication myApplication = null;
    private Activity currentActivity;
    private static boolean isShowingAd = false;
    private long loadTime = 0;




    public AppOpenAdManager(CalcApplication myApplication) {
        this.myApplication = myApplication;
        this.myApplication.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(ON_START)
    public void onStart() {
        showAdIfAvailable();
        Log.d("AppOpenAdmanager", "onStart");
    }

    public void fetchAd(){

        if (isAdAvailable()) {
            return;
        }

        loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull @org.jetbrains.annotations.NotNull AppOpenAd appOpenAd) {
                super.onAdLoaded(appOpenAd);
                AppOpenAdManager.this.appOpenAd = appOpenAd;
                AppOpenAdManager.this.loadTime = (new Date()).getTime();
            }

            @Override
            public void onAdFailedToLoad(@NonNull @org.jetbrains.annotations.NotNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.d("AppOpenAdManager",loadAdError.toString());
            }
        };

        AdRequest request = getAdRequest();
        AppOpenAd.load(myApplication, myApplication.getString(R.string.admob_app_open_ads),request,
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);

    }

    public void showAdIfAvailable(){
        if (!isShowingAd && isAdAvailable()){
            Log.d("AppOpenAdManager","Showing App Open Ad");
            FullScreenContentCallback fullScreenContentCallback =
                    new FullScreenContentCallback(){
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            AppOpenAdManager.this.appOpenAd = null;
                            isShowingAd = false;
                            fetchAd();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);
                            Log.d("AppOpenAdManager",adError.getMessage());
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            super.onAdShowedFullScreenContent();
                            isShowingAd = true;
                        }
                    };
            appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
            appOpenAd.show(currentActivity);
        }
        else{
            Log.d("AppOpenAdManager", "Can not show ad.");
            fetchAd();
        }
    }

    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    private boolean wasLoadTimeLessThanNHoursAgo(long numHours) {
        long dateDifference = (new Date()).getTime() - this.loadTime;
        long numMilliSecondsPerHour = 3600000;
        return (dateDifference < (numMilliSecondsPerHour * numHours));
    }

    public boolean isAdAvailable() {
        return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        currentActivity=activity;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentActivity=activity;
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        currentActivity=null;
    }
}
