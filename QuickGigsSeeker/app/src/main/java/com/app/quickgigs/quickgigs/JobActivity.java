package com.app.quickgigs.quickgigs;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JobActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        final Intent i = getIntent();
        getSupportActionBar().setTitle(i.getStringExtra("NAME"));
        ImageView locationButton = (ImageView) findViewById(R.id.imageView);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strUri = "http://maps.google.com/maps?q=loc:30.2952756,-97.6226723(" + i.getStringExtra("NAME") + ")" ;
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });


        TextView priceText = (TextView) findViewById(R.id.price_text);
        TextView detailsText = (TextView) findViewById(R.id.details);
        detailsText.setText(i.getStringExtra("DETAILS"));
        mDatabase = FirebaseDatabase.getInstance().getReference();
        priceText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child("open-jobs/" + i.getStringExtra("UID")).removeValue();
                mDatabase.child("claimed-jobs/" + i.getStringExtra("UID")).setValue(new Job(i.getStringExtra("UID"), i.getStringExtra("NAME"), i.getIntExtra("PAY", 0), i.getStringExtra("DETAILS")));
                finish();
            }
        });

        ImageView callButton = (ImageView) findViewById(R.id.imageView2);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse("tel:18327481115") );
                startActivity( browse );
            }
        });
        priceText.setText("$" + i.getIntExtra("PAY", 0));
        priceText.setScaleY(0f);
        priceText.setScaleX(0f);
        priceText.animate().scaleX(1f).scaleY(1f).setDuration(250).setStartDelay(500).setInterpolator(new OvershootInterpolator()).start();
        ImageView bossPic = (ImageView) findViewById(R.id.boss_pic);
        bossPic.setScaleY(0);
        bossPic.setScaleX(0);
        bossPic.animate().scaleX(1f).scaleY(1f).setDuration(500).setStartDelay(750).setInterpolator(new OvershootInterpolator()).start();

    }
}
