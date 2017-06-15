package com.kahawa.development.kahawasystem.Models.Beans;

/**
 * Created by Mwatha on 17-Aug-16.
 */
public class FavModel {
    String fav_foodname, favhoelname,date;

    public FavModel(String fav_foodname, String Ldate) {
        this.fav_foodname = fav_foodname;
        this.date=Ldate;

    }

    public FavModel() {
    }

    public String getFav_foodname() {
        return fav_foodname;
    }

    public void setFav_foodname(String fav_foodname) {
        this.fav_foodname = fav_foodname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
