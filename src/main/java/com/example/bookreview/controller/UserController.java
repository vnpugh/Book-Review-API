package com.example.bookreview.controller;


import com.example.bookreview.model.User;
import com.example.bookreview.model.request.LoginRequest;
import com.example.bookreview.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping(path = "/auth/users")

public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    // http://localhost:9092/auth/users/register/
    @PostMapping("/register/")
    public User createUser(@RequestBody User userObject) {
        return userService.createUser(userObject);
    }


    //login account and create a book review
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        ResponseEntity<?> loggedInUser = userService.loginUser(loginRequest);
        return ResponseEntity.ok(loggedInUser);
    }










}
