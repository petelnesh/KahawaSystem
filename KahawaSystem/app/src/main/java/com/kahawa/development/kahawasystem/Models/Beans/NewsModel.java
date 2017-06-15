package com.kahawa.development.kahawasystem.Models.Beans;

import java.text.SimpleDateFormat;

public class NewsModel {

    private String title,time,content,image,location;
    public NewsModel() {
    }
    public NewsModel(String title, String time, String content, String image, String  location) {
        this.title = title;
        this.time = time;
        this.content = content;
        this.location=location;
        this.image=image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
