package com.kahawa.development.kahawasystem.UI.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.kahawa.development.kahawasystem.R;


public class ActivitySell extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_sell);
    }

    public void uploadImage(View view) {
        Toast.makeText(this,"Hello!",Toast.LENGTH_SHORT).show();
    }
}
