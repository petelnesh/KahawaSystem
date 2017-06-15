package com.kahawa.development.kahawasystem.UI.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kahawa.development.kahawasystem.Configs.Configurations;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kahawa.development.kahawasystem.Models.Databases.DatabaseHandler;
import com.kahawa.development.kahawasystem.R;


public class AddTopic extends AppCompatActivity {

    private Button addTopic;
    private EditText topicName;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private String name;
    private ArrayList<String> list_of_topics = new ArrayList<>();

    private DatabaseReference root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_layout);
        Toolbar toolbar= (Toolbar) findViewById(R.id.topic_toolbar);
        setSupportActionBar(toolbar);
        setTitle("Topics under discussion");
        addTopic=(Button) findViewById(R.id.addTopic);
        topicName=(EditText) findViewById(R.id.newTopic);
        listView=(ListView) findViewById(R.id.list);
        arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list_of_topics);
        listView.setAdapter(arrayAdapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        root= FirebaseDatabase.getInstance().getReference().getRoot();
        root.keepSynced(true);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(),Chatroom.class);
                intent.putExtra("topic_name",((TextView)view).getText().toString());
                SharedPreferences sharedPreferences=getSharedPreferences(Configurations.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                name=sharedPreferences.getString("username","");
                intent.putExtra("username",name);
                startActivity(intent);
            }
        });

        addTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> map= new HashMap<>();
                String topic=topicName.getText().toString();
                map.put(topic,"");
                root.updateChildren(map);
                topicName.setText("");
            }
        });
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<String> set= new HashSet<>();
                Iterator i= dataSnapshot.getChildren().iterator();
                while (i.hasNext())
                {
                    set.add(((DataSnapshot)i.next()).getKey());
                }
                list_of_topics.clear();
                list_of_topics.addAll(set);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.logout)
        {
                logOut();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logOut() {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Logging out");
        builder.setMessage("Ar you sure you want to log out?");
        builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Getting out sharedpreferences
                SharedPreferences sharedPreferences=getSharedPreferences(Configurations.SHARED_PREF_NAME,Context.MODE_PRIVATE);
                //Getting editor
                SharedPreferences.Editor editor = sharedPreferences.edit();

                //Putting the value false for loggedin
                editor.putBoolean(Configurations.LOGGEDIN_SHARED_PREF, false);

                //Putting blank value to email
                editor.putString(Configurations.USERNAME_SHARED_PREF, "");

                //Saving the sharedpreferences
                editor.apply();

                //Starting login activity
                Intent intent = new Intent(AddTopic.this, ActivityNews.class);
                startActivity(intent);
            }
        });
        builder.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog= builder.create();
        alertDialog.show();
    }

}
