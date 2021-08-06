package com.duy.ncalc;

import android.content.Context;
import android.util.Log;

import com.duy.calculator.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class AdManager {
    public static com.google.android.gms.ads.InterstitialAd interstitialAd;
    private Context context;

    public AdManager(Context context)
    {
        this.context = context;
        createAd();
    }

    public void createAd()
    {
        interstitialAd =new InterstitialAd(context);
        interstitialAd.setAdUnitId(context.getString(R.string.admob_inter_start_id));
        final AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);
    }

    public InterstitialAd getInterstitialAd()
    {
        return interstitialAd;
    }
}
