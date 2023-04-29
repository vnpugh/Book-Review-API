package com.example.bookreview.controller;

import com.example.bookreview.model.BestSeller;
import com.example.bookreview.model.Book;
import com.example.bookreview.service.BestSellerService;
import com.example.bookreview.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController //will handle HTTP requests -> separating the logic
@RequestMapping(path = "/api") // http://localhost:9092/api
public class BestSellerController {

    private BestSellerService bestSellerService;

    @Autowired
    public void setBestSellerService(BestSellerService bestSellerService) {
        this.bestSellerService = bestSellerService;
    }

    @GetMapping(path = "/bestsellers/") // http://localhost:9092/api/books/
    public List<BestSeller> getBestSellers() {
        return bestSellerService.getAllBestSellers();
    }










}
