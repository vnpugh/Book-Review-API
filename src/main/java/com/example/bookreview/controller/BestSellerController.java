package com.example.bookreview.controller;

import com.example.bookreview.model.BestSeller;
import com.example.bookreview.model.Book;
import com.example.bookreview.service.BestSellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController //will handle HTTP requests -> separating the logic
@RequestMapping(path = "/api") // http://localhost:9092/api
public class BestSellerController {

    private BestSellerService bestSellerService;

    @Autowired
    public void setBestSellerService(BestSellerService bestSellerService) {
        this.bestSellerService = bestSellerService;
    }

    @GetMapping(path = "/bestsellers/") // http://localhost:9092/api/bestsellers/
    public List<BestSeller> getBestSellers() { //retrieves a list of all bestsellers
        return bestSellerService.getAllBestSellers();
    }


    /**
     *retrieves a filtered list with optional query parameters author, title,
      genre, weeks, sales, and rating.
     * @param author
     * @param title
     * @param genre
     * @param weeks
     * @param sales
     * @param rating
     * @return
     */
    @GetMapping(path = "/bestsellers/search")//http://localhost:9092/api/bestsellers/search/
    public List<BestSeller> searchBestSellers(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Integer weeks,
            @RequestParam(required = false) Integer sales,
            @RequestParam(required = false) Double rating) {

        return bestSellerService.searchBestSellers(author, title, genre, weeks, sales, rating);
    }



}











