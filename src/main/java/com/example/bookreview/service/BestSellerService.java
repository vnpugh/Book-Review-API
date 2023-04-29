package com.example.bookreview.service;


import com.example.bookreview.exception.InformationNotFoundException;
import com.example.bookreview.model.BestSeller;
import com.example.bookreview.model.Book;
import com.example.bookreview.model.User;
import com.example.bookreview.repository.BestSellerRepository;
import com.example.bookreview.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BestSellerService {
    private BestSellerRepository bestSellerRepository;

    @Autowired
    public void setBestSellerRepository(BestSellerRepository bestSellerRepository) {
        this.bestSellerRepository = bestSellerRepository;
    }

    public static User getCurrentLoggedInUser() { //retrieves the current logged-in user's details.
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return userDetails.getUser();
    }

    /**
     *using BestSellerRepository to find all bestselling books associated with the user id.
     *@return bestsellers
     */
    public List<BestSeller> getAllBestSellers() { //retrieves all bestselling books associated with the user id.
        long userId = BestSellerService.getCurrentLoggedInUser().getId();
        List<BestSeller> bestSellers = bestSellerRepository.findByUserId(userId);
        if (bestSellers.isEmpty()) {
            throw new InformationNotFoundException("no bestselling books found for user id " + userId);
        }
        return bestSellers;
    }
    public List<BestSeller> getBestSellingBooks(String author, String title, String genre, Integer weeks,
                 Double rating, Integer sales) {
        List<BestSeller> bestSellers = getAllBestSellers();

        try {
            if (author != null) {   //ignore case added -> the search will ignore case sensitivity.
                bestSellers = bestSellerRepository.findByAuthorIgnoreCase(author);
            }

            if (title != null) {
                bestSellers = bestSellerRepository.findByTitleIgnoreCase(title);
            }

            if (genre != null) {
                bestSellers = bestSellerRepository.findByGenreIgnoreCase(genre);
            }

            if (weeks != null) {
                bestSellers = bestSellerRepository.findByWeeks(weeks);
            }

            if (rating != null) {
                bestSellers = bestSellerRepository.findByRating(rating);
            }

            if (sales != null) {
                bestSellers = bestSellerRepository.findBySales(sales);
            }
        } catch (DataAccessException e) {
            throw e; // just rethrow the original exception
        }

        return bestSellers;
    }
}


