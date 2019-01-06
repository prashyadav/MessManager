package com.example.prashanjeet.messmanager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prajjwal-ubuntu on 4/1/19.
 */

public class UserMeal {
    public String name,regNumber;
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
//        list.add(new ArrayList<Integer>());

        //List<Integer> list1 = new ArrayList<Integer>();
        for(int i=0;i<=31;i++){
            for(int j=0; j<=12;j++){
                list.add(0);
            }
        }
    }
}
