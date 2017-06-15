package com.kahawa.development.kahawasystem.UI.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kahawa.development.kahawasystem.Configs.Configurations;
import com.kahawa.development.kahawasystem.R;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    AutoCompleteTextView autoUsername;
    EditText logPassword;
    AppCompatButton logIn;
    //boolean variable to check user is logged in or not
    //initially it is false
    private boolean loggedIn = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Log In here");
        autoUsername=(AutoCompleteTextView) findViewById(R.id.username);

        logPassword=(EditText) findViewById(R.id.password);
        logIn=(AppCompatButton) findViewById(R.id.sign_in_button);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DatabaseHandler db= new DatabaseHandler(getApplicationContext());
                //db.getUser(username,pass);
                sendToserver();
            }
        });
    }

    private void sendToserver() {

        final String pass=logPassword.getText().toString().trim();
        final String username1=autoUsername.getText().toString().trim();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Configurations.LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                if(s.equalsIgnoreCase(Configurations.LOGIN_SUCCESS))
                {
                    SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Configurations.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    //Adding values to editor
                    editor.putBoolean(Configurations.LOGGEDIN_SHARED_PREF, true);
                    editor.putString(Configurations.USERNAME_SHARED_PREF, username1);

                    //Saving values to editor
                    editor.apply();

                    //Starting profile activity
                    Intent intent = new Intent(LoginActivity.this, AddTopic.class);
                    intent.putExtra("username",username1);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Invalid credentials",Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put(Configurations.KEY_USERNAME, username1);
                params.put(Configurations.KEY_PASSWORD, pass);

                //returning parameter
                return params;

            }};
        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        }

   @Override
    protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(Configurations.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(Configurations.LOGGEDIN_SHARED_PREF, false);
        //If we will get true
        if(loggedIn){
            //We will start the chat room Activity
            Intent intent = new Intent(LoginActivity.this, AddTopic.class);
            startActivity(intent);
        }}
    }