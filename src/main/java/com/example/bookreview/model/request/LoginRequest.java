package com.example.bookreview.model.request;

public class LoginRequest {
    //need email and password for user to login
    private String email;
    private String password;

    //only getters to get the info from the user

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}