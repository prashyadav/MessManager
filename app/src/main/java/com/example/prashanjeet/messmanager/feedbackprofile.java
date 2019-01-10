package com.example.prashanjeet.messmanager;

/**
 * Created by PRASHANJEET on 09-01-2019.
 */

public class feedbackprofile {
    public  String  meal;
    public  String feedback;
    public String ratingvalue;
    public String  date;
    public feedbackprofile(String meal, String feedback,String date ,String ratingvalue) {
        this.meal = meal;
        this.feedback = feedback;
        this.date = date;
        this.ratingvalue = ratingvalue;
    }

}
