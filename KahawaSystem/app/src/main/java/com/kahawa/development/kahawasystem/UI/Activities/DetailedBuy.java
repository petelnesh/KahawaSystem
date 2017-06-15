package com.kahawa.development.kahawasystem.UI.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kahawa.development.kahawasystem.Configs.Configurations;
import com.kahawa.development.kahawasystem.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DetailedBuy extends AppCompatActivity {
    TextView proName,proCost,displayer,total_cost;
    NetworkImageView imageView;
    private ImageLoader mImageLoader;
    private RequestQueue mRequestQueue;
    int limit=10;
    int currentitems;
    String imageurl,cost,name;
    int total_sum;
    int my_amount;
    Button buy_goods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_buy);

        Toolbar toolbar=(Toolbar) findViewById(R.id.buy_toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRequestQueue = Volley.newRequestQueue(this);
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });
        imageurl=getIntent().getExtras().getString("imageurl");
        cost=getIntent().getExtras().getString("cost");
        name=getIntent().getExtras().getString("name");


        imageView=(NetworkImageView) findViewById(R.id.buyimage);
        proCost=(TextView) findViewById(R.id.cost_of_product);
        proName=(TextView) findViewById(R.id.name_of_product);
        displayer=(TextView) findViewById(R.id.displayer);
        total_cost=(TextView) findViewById(R.id.total_cost);
        imageView.setImageUrl(imageurl,mImageLoader);
        proCost.setText(cost);
        proName.setText(name);
        buy_goods=(Button) findViewById(R.id.buy_goods);
        buy_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buy();
            }
        });


    }
    public void add(View view) {
        currentitems=Integer.parseInt(displayer.getText().toString());
        if (currentitems<limit){
            currentitems++;
            displayer.setText(String.valueOf(currentitems));
            total_sum=Integer.parseInt(proCost.getText().toString());
            my_amount=total_sum*currentitems;
            printTotal();
        }
        else
        if(currentitems==limit)
        {

            Snackbar.make(view, "You are not allowed to order more than 10 items for each", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    public void minus(View view) {
        currentitems=Integer.parseInt(displayer.getText().toString());
        if(currentitems==1){
            displayer.setText(String.valueOf(currentitems));
            Snackbar.make(view, "1 is the minimum number you can order", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            total_sum=Integer.parseInt(proCost.getText().toString());
            my_amount=total_sum*currentitems;
            printTotal();
        }
        else
            currentitems--;
        displayer.setText(String.valueOf(currentitems));
        total_sum=Integer.parseInt(proCost.getText().toString());
        my_amount=total_sum*currentitems;
        printTotal();
    }
    private void printTotal() {
        total_cost.setText(String.valueOf(my_amount));
    }

    public void buy() {
        final String name=proName.getText().toString().trim(),cost=String.valueOf(my_amount);

        StringRequest stringRequest= new StringRequest(Request.Method.POST,
                Configurations.BUY_GOODS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                    Toast.makeText(getApplicationContext(),"successful",Toast.LENGTH_SHORT).show();
                    finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String date=new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put(Configurations.BUY_GOODS_NAME,name);
                params.put(Configurations.BUY_GOODS_COST, cost);
                params.put(Configurations.BUY_GOODS_DATE_BOUGHT, date);

                //returning parameter
                return params;

            }};

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
