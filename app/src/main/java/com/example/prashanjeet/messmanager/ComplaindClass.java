package com.example.prashanjeet.messmanager;

/**
 * Created by PRASHANJEET on 10-01-2019.
 */

public class ComplaindClass {
    String message,name,email;

    public  ComplaindClass(){

    }

    public ComplaindClass(String message, String name, String email) {
        this.message = message;
        this.name = name;
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

