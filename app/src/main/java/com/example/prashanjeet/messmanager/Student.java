package com.example.prashanjeet.messmanager;

/**
 * Created by PRASHANJEET on 04-01-2019.
 */

public class Student {
    private String name,mobile,regNumber,hostel,roomNo,email;
    private String id,mealId;
    private String status;
    public Student(){

    }

    public Student(String name, String mobile, String regNumber, String hostel, String roomNo, String email, String id, String mealId,String status) {
        this.name = name;
        this.mobile = mobile;
        this.regNumber = regNumber;
        this.hostel = hostel;
        this.roomNo = roomNo;
        this.email = email;
        this.id = id;
        this.mealId= mealId;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getHostel() {
        return hostel;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMealId() {
        return mealId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }
}
