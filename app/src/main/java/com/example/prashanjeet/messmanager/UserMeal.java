package com.example.prashanjeet.messmanager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prajjwal-ubuntu on 4/1/19.
 */

public class UserMeal {
    public String name,regNumber;
    public int totalMeals,balance;
    //public int[][] meals = new int[12][31];
    public List<Integer> list;

    //ArrayList<Integer>[][] list = new ArrayList[12][31];


    public UserMeal(){
        this.list =new ArrayList<>();
//        list.add(new ArrayList<Integer>());

        //List<Integer> list1 = new ArrayList<Integer>();
        for(int i=0;i<=31;i++){
            for(int j=0; j<=12;j++){
                list.add(0);
            }
        }


    }

    public UserMeal(String name,String regNumber){
        this.name = name;
        this.regNumber= regNumber;
        this.list =new ArrayList<>();
        this.totalMeals = 0;
        this.balance = 15000;
//        list.add(new ArrayList<Integer>());

        //List<Integer> list1 = new ArrayList<Integer>();
        for(int i=0;i<=31;i++){
            for(int j=0; j<=12;j++){
                list.add(0);
            }
        }
    }

    public UserMeal(String name, String regNumber, int totalMeals, int balance, List<Integer> list) {
        this.name = name;
        this.regNumber = regNumber;
        this.totalMeals = totalMeals;
        this.balance = balance;
        this.list = list;
    }

    public int getTotalMeals() {
        return totalMeals;
    }

    public void setTotalMeals(int totalMeals) {
        this.totalMeals = totalMeals;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }
}
