package com.kahawa.development.kahawasystem.UI.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.kahawa.development.kahawasystem.Adapters.ChatAdapter;
import com.kahawa.development.kahawasystem.Models.Beans.Message;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kahawa.development.kahawasystem.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Chatroom extends AppCompatActivity {

    private ImageButton send_Msg;
    private EditText message;
    private String topic,username;
    private DatabaseReference root;
    private  String temp_key;
    ListView messageListView;
    ChatAdapter chatAdapter;
    public ArrayList<Message> chatlist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        send_Msg=(ImageButton) findViewById(R.id.button);
        message=(EditText) findViewById(R.id.message);
        messageListView=(ListView) findViewById(R.id.listChat);
//view the latest message first
        messageListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        messageListView.setStackFromBottom(true);

        chatAdapter=new ChatAdapter(Chatroom.this,chatlist);
        messageListView.setAdapter(chatAdapter);
        Toolbar toolbar= (Toolbar) findViewById(R.id.tolbar);
        setSupportActionBar(toolbar);

        topic=getIntent().getExtras().getString("topic_name").toString();
        username=getIntent().getExtras().getString("username").toString();

        final String dateStamp=new SimpleDateFormat("HH:mm dd/MM").format(new Date());
        setTitle("Topic - "+ topic);
        root= FirebaseDatabase.getInstance().getReference().child(topic);
        send_Msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> map= new HashMap<>();
                temp_key=root.push().getKey();
                root.updateChildren(map);
                DatabaseReference messageroot=root.child(temp_key);
                Map<String,Object> map2= new HashMap<>();
                map2.put("name",username);
                map2.put("msg",message.getText().toString());
                map2.put("time",dateStamp);
                messageroot.updateChildren(map2);
                message.setText("");
            }
        });
        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                append_chat_conv(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                append_chat_conv(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void append_chat_conv(DataSnapshot dataSnapshot) {
        String message,chat_username,date;
        Iterator i= dataSnapshot.getChildren().iterator();
        while (i.hasNext())
        {
            message=(String)((DataSnapshot)i.next()).getValue();
            chat_username=(String)((DataSnapshot)i.next()).getValue();
            date=(String)((DataSnapshot)i.next()).getValue();

            Message message1=new Message(chat_username,message,date,username);
            message1.setSender(chat_username);
            message1.setMessage(message);
            message1.setTime(date);
            //adding the data to our adapter
            chatlist.add(message1);
            chatAdapter.notifyDataSetChanged();
        }
    }

}
