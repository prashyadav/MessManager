package com.example.prashanjeet.messmanager;

/**
 * Created by PRASHANJEET on 09-01-2019.
 */

public class feedbackprofile {
    public  String  meal;
    public  String feedback1;
    public String ratingvalue;
    public String  date;
    public feedbackprofile(String meal, String feedback1,String date ,String ratingvalue) {
        this.meal = meal;
        this.feedback1 = feedback1;
        this.date = date;
        this.ratingvalue = ratingvalue;
    }
    public  feedbackprofile(){

    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public String getFeedback1() {
        return feedback1;
    }

    public void setFeedback1(String feedback) {
        this.feedback1 = feedback;
    }

    public String getRatingvalue() {
        return ratingvalue;
    }

    public void setRatingvalue(String ratingvalue) {
        this.ratingvalue = ratingvalue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
