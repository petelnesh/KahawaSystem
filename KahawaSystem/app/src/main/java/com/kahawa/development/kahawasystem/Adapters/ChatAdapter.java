package com.kahawa.development.kahawasystem.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kahawa.development.kahawasystem.Models.Beans.Message;
import com.kahawa.development.kahawasystem.R;
import com.kahawa.development.kahawasystem.UI.Activities.Chatroom;

import java.util.ArrayList;

/**
 * Created by Mwatha on 02-Sep-16.
 */

public class ChatAdapter extends ArrayAdapter {

    private static LayoutInflater inflater = null;
    ArrayList<Message> chatMessageList;

    public ChatAdapter(Activity activity, ArrayList<Message> list) {
        super(activity,R.layout.chat_bubble,list);
        chatMessageList = list;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return chatMessageList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = chatMessageList.get(position);
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.chat_bubble, null);

        TextView msg = (TextView) vi.findViewById(R.id.message_text);
        TextView sender=(TextView)vi.findViewById(R.id.sender_text);
        TextView date_time=(TextView)vi.findViewById(R.id.date_time);
        LinearLayout layout = (LinearLayout) vi
                .findViewById(R.id.bubble_layout);
        LinearLayout parent_layout = (LinearLayout) vi
                .findViewById(R.id.bubble_layout_parent);


        // if message is from my username then align to right
        if (message.isME.equals(message.getSender())) {
            layout.setBackgroundResource(R.drawable.bubble2);
            parent_layout.setGravity(Gravity.RIGHT);
            sender.setTextColor(Color.BLACK);
        }
        // If not mine then align to left
        else {
            layout.setBackgroundResource(R.drawable.bubble1);
            parent_layout.setGravity(Gravity.LEFT);
            sender.setTextColor(Color.BLUE);
        }
        msg.setTextColor(Color.BLACK);
        msg.setText(message.getMessage());
        sender.setText(message.getSender());
        date_time.setText(message.getTime());
        return vi;
    }
}