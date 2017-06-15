package com.kahawa.development.kahawasystem.Models.Beans;


/**
 * Created by Mwatha on 02-Sep-16.
 */
public class Message {
    public String sender,message,time;
    public String isME;
    public Message(String sender,String message, String time,String isMe) {
        this.sender = sender;
        this.message = message;
        this.time = time;
        this.isME=isMe;
    }

    public Message() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
             this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
