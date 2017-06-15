package com.kahawa.development.kahawasystem.Models.Beans;

/**
 * Created by Mwatha on 05-Sep-16.
 */
public class BuyModel {
    String image,cost,name;

    public BuyModel(String image, String cost, String name) {
        this.image = image;
        this.cost = cost;
        this.name = name;
    }

    public BuyModel() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
