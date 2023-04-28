package com.example.bookreview.controller;


import com.example.bookreview.model.User;
import com.example.bookreview.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    // http://localhost:9092/auth/users/register/
    //need to create a user endpoint for logging in
    @PostMapping("/login/")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        return userService.loginUser(loginRequest);
    }

}
