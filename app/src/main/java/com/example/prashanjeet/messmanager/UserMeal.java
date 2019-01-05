package com.example.prashanjeet.messmanager;

/**
 * Created by prajjwal-ubuntu on 4/1/19.
 */

public class UserMeal {
    public String name,regNumber;
    int[][] meals = new int[12][31];
    public UserMeal(){
        for(int i=0; i<12;i++){
            for(int j=0; j<31;j++){
                this.meals[i][j]=0;
            }
        }
    }

    public UserMeal(String name,String regNumber){
        this.name = name;
        this.regNumber= regNumber;
        for(int i=0; i<12;i++){
            for(int j=0; j<31;j++){
                this.meals[i][j]=0;
            }
        }
    }
}
