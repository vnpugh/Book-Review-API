package com.example.bookreview.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class HelloWorld {

    //http://localhost:9092/api/hello/
    @GetMapping(path = "/hello")
    public String helloWorld(){
        return "Hello World";
    }
}