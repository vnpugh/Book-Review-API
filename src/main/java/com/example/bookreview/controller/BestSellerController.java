package com.example.bookreview.controller;

import com.example.bookreview.model.BestSeller;
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
    public List<BestSeller> getBestSellers() { //retrieves a list of bestsellers
        return bestSellerService.getAllBestSellers();
    }

    /**
     *search for bestsellers by specifying parameters.
     * @param author
     * @param title
     * @param genre
     * @param weeks
     * @param sales
     * @param rating
     * @return bestsellers
     */

    @GetMapping(path = "/bestsellers/search")//http://localhost:9092/api/bestselling/search/
    public List<BestSeller> searchBestSellingBooks(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Integer weeks,
            @RequestParam(required = false) Integer sales,
            @RequestParam(required = false) Double rating) {

        List<BestSeller> bestSellers = bestSellerService.getBestSellers();

        if (author != null) {
            bestSellers = bestSellers.stream().filter(bestseller -> bestseller.getAuthor().equalsIgnoreCase(author)).collect(Collectors.toList());
        }
        if (title != null) {
            bestSellers = bestSellers.stream().filter(bestseller -> bestseller.getTitle().equalsIgnoreCase(title)).collect(Collectors.toList());
        }
        if (genre != null) {
            bestSellers = bestSellers.stream().filter(bestseller -> bestseller.getGenre().equalsIgnoreCase(genre)).collect(Collectors.toList());
        }
        if (weeks != null) {
            bestSellers = bestSellers.stream().filter(bestseller -> bestseller.getWeeks().equals(weeks))
                    .collect(Collectors.toList());
        }
        if (sales != null) {
            bestSellers = bestSellers.stream().filter(bestseller -> bestseller.getSales().equals(sales))
                    .collect(Collectors.toList());
        }
        if (rating != null) {
            bestSellers = bestSellers.stream().filter(bestseller -> bestseller.getRating() == rating)
                    .collect(Collectors.toList());
        }
        return bestSellers;
    }
}











