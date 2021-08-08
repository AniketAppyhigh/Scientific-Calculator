package com.duy.ncalc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.duy.calculator.R;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class SmartRating {

    private static SmartRating Instance;
    private SmartRating() {

    }

    public void showRatingDialog(final Activity activity) {
        View view = LayoutInflater.from(activity).inflate(R.layout.inapp_rating_dialog, null, false);
        final AlertDialog dialog = new AlertDialog.Builder(activity)
                .create();
        dialog.setView(view);

        TextView rateNow = view.findViewById(R.id.rate_now);
        TextView rateLater = view.findViewById(R.id.rate_later);
        final MaterialRatingBar ratingValue = view.findViewById(R.id.rating_bar);

        dialog.show();

        rateNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating  = ratingValue.getRating();
                dialog.dismiss();
                if (rating > 3.0f) {
                    activity.startActivity(
                            new Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://play.google.com/store/apps/details?id=" + activity.getPackageName())
                            )
                    );
                } else {
                    Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();
                }

            }
        });

        rateLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Thanks for the feedback", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

    }

    public static SmartRating getInstance() {
        if (Instance == null) {
            synchronized (SmartRating.class) {
                if (Instance == null) {
                    Instance = new SmartRating();
                }
            }
        }
        return Instance;
    }
}
