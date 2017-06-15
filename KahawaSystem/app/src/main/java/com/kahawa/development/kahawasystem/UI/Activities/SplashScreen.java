package com.kahawa.development.kahawasystem.UI.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kahawa.development.kahawasystem.R;


public class SplashScreen extends AppCompatActivity {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private static  final String PREF_NAME="Installed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        boolean hasVisited = sp.getBoolean("hasVisited",
                false);
        if (!hasVisited) {
            setContentView(R.layout.activity_splash_screen);
            Thread timer = new Thread() {
                public void run() {
                    try {
                        sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        startActivity(new Intent(SplashScreen.this, ActivityNews.class));
                    }
                }

            };
            timer.start();
        }
        else if(hasVisited)
            startActivity(new Intent(SplashScreen.this,ActivityNews.class));
        SharedPreferences.Editor e = sp.edit();
        e.putBoolean("hasVisited", true);
        e.apply();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
