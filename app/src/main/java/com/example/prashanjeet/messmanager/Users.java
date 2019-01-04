package com.example.prashanjeet.messmanager;

/**
 * Created by PRASHANJEET on 04-01-2019.
 */

public class Users {
    String userName;
    String userReg;
    String userId;
    public Users(){}

    public Users(String userId, String userName, String userReg) {
        this.userName = userName;
        this.userReg = userReg;
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserReg() {
        return userReg;
    }

    public void setUserReg(String userReg) {
        this.userReg = userReg;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
