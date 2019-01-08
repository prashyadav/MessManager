package com.example.prashanjeet.messmanager;

/**
 * Created by prajjwal-ubuntu on 4/1/19.
 */

public class Meal {
    public String title,description,expectedCost,date;
    public int val,registered;
    Meal() {

    }

    public Meal(String title, String description, String expectedCost, String date) {
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
    }
}
