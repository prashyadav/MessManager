package com.example.prashanjeet.messmanager;

/**
 * Created by PRASHANJEET on 10-01-2019.
 */

public class AdminExpense {
    int cost,month;
    String desc;
    String date;


    public AdminExpense(int cost, String desc,String date,int month) {
        this.cost = cost;
        this.desc = desc;
        this.date = date;
        this.month = month;
    }
    public AdminExpense(){

    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

