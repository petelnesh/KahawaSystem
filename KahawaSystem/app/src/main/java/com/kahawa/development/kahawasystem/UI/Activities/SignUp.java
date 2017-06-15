package com.kahawa.development.kahawasystem.UI.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import com.kahawa.development.kahawasystem.Models.Databases.DatabaseHandler;
import com.kahawa.development.kahawasystem.R;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    EditText fname,username,password,confirm_password;
    AppCompatButton sign_up;
    View focusView=null;
    private boolean loggedIn = false;
    String firstname,passwordText,usernameText,confirmpasswordText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Sign up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setUpData();
    }

    private void setUpData() {

        fname=(EditText) findViewById(R.id.input_fname);
        username=(EditText) findViewById(R.id.input_username);
        password=(EditText) findViewById(R.id.input_password);
        confirm_password=(EditText) findViewById(R.id.input_confirm_password);
        sign_up=(AppCompatButton) findViewById(R.id.signup);

        fname.requestFocus();
        firstname=fname.getText().toString();
        usernameText=username.getText().toString();
        passwordText=password.getText().toString();
        confirmpasswordText=confirm_password.getText().toString();

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fname.getText().toString().isEmpty() || username.getText().toString().isEmpty())
                {
                    fname.setError("Required");
                    focusView=fname;

                    username.setError("Required");
                    focusView=username;


                    focusView.requestFocus();
                }
                else if(!password.getText().toString().equals(confirm_password.getText().toString()))
                {
                    confirm_password.setError("password do not match!");
                    focusView=confirm_password;

                    focusView.requestFocus();
                }
                else if(password.getText().toString().length()<8 || confirm_password.getText().toString().length()<8)
                {
                    password.setError("Password should be 8 or more characters");
                    focusView=password;
                    focusView.requestFocus();
                }
                else {

                    signup();
                    SharedPreferences sharedPreferences=getSharedPreferences(Configurations.SHARED_PREF_NAME,Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putBoolean(Configurations.CREATEDACCOUNT_SHARED_PREF,true);
                    editor.apply();
                    DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                    db.addUser(firstname,usernameText,passwordText);
                }

            }
        });

    }

    private void signup() {
        final String username_u = username.getText().toString().trim();
        final String password_u = password.getText().toString().trim();
        final String firstname_u = fname.getText().toString().trim();

        StringRequest stringRequest= new StringRequest(Request.Method.POST, Configurations.REGISTER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
            if(s.equalsIgnoreCase(Configurations.REGISTRATION_FAILURE))
            {
                Toast.makeText(getApplicationContext(),"Username already exists!",Toast.LENGTH_SHORT).show();
            }
                else {
                startActivity(new Intent(SignUp.this, LoginActivity.class));
                finish();

            }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put(Configurations.KEY_REGISTER_NAME,firstname_u);
                params.put(Configurations.KEY_REGISTER_USERNAME, username_u);
                params.put(Configurations.KEY_REGISTER_PASSWORD, password_u);

                //returning parameter
                return params;

            }};

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void logIn(View view) {
        startActivity(new Intent(SignUp.this,LoginActivity.class));
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
            Intent intent = new Intent(SignUp.this, AddTopic.class);
            startActivity(intent);
        }}
}
