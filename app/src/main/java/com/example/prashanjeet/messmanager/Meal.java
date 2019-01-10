package com.example.prashanjeet.messmanager;

/**
 * Created by prajjwal-ubuntu on 4/1/19.
 */

public class Meal {
    public String title,description,date,expectedCost,id,registration;
    public int val,registered;

    Meal() {

    }

    public Meal(String title, String description, String expectedCost, String date,String id) {
        this.title = title;
        this.description = description;
        this.expectedCost = expectedCost;
        int val1;
        if(title.compareTo("breakfast")==0){
            val1=1;
        }
        else if(title.compareTo("lunch")==0){
            val1=2;
        }
        else if(title.compareTo("snacks")==0){
            val1=4;
        }
        else {
            val1=8;
        }
        this.val = val1;
        this.date = date;
        this.registered = 0;
        this.id = id;
        this.registration="open";
    }

    public String getExpectedCost() {
        return expectedCost;
    }

    public void setExpectedCost(String expectedCost) {
        this.expectedCost = expectedCost;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public int getRegistered() {
        return registered;
    }

    public void setRegistered(int registered) {
        this.registered = registered;
    }
}
