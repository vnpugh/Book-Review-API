package com.example.bookreview.controller;



import com.example.bookreview.exception.UserNotFoundException;
import com.example.bookreview.exception.UserNotLoggedInException;
import com.example.bookreview.model.User;
import com.example.bookreview.model.request.LoginRequest;

import com.example.bookreview.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/register/") //user can register/create a new account
    public User createUser(@RequestBody User userObject) {
        return userService.createUser(userObject);
    }


    //login account and create a book review
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        ResponseEntity<?> loggedInUser = userService.loginUser(loginRequest);
        return ResponseEntity.ok(loggedInUser);
    }


    /**
     * method to update user by userId
     * @throws UserNotFoundException
     * @throws UserNotLoggedInException
     */
    @PutMapping("/users/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody User userObject) throws UserNotFoundException,
            UserNotLoggedInException {
        return userService.updateUser(userId, userObject);
    }

    /**
     * method to find user by email address.
     * userService is used to find user by email address.
     * @param email
     * @throws UserNotFoundException
     * @throws UserNotLoggedInException
     */
    @GetMapping("/users/{email}/")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.findUserByEmailAddress(email);
        if (user == null) {
            return ResponseEntity.notFound().build(); //404 status code
        }
        return ResponseEntity.ok(user); //200 status code
    }

}

