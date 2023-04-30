package com.example.bookreview.service;


import com.example.bookreview.exception.InformationNotFoundException;
import com.example.bookreview.model.Review;
import com.example.bookreview.model.User;
import com.example.bookreview.repository.ReviewRepository;
import com.example.bookreview.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class ReviewService {
    private static ReviewRepository reviewRepository;


    @Autowired //the component can use the ReviewRepository to perform database operations
    public void setReviewRepository(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }


    public static User getCurrentLoggedInUser() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return userDetails.getUser();
    }
    public List<Review> getReviewsByUser(Long userId) {
        List<Review> reviews = reviewRepository.findByUserId(userId);
        if (reviews.isEmpty()) {
            throw new InformationNotFoundException("no reviews found for user id " + userId);
        }
        return reviews;
    }

    public List<Review> getReviewsByReviewDate(LocalDate reviewDate) {
        return reviewRepository.findByReviewDate(reviewDate);
    }



    public List<Review> getReviewsByRating(double rating) {
        return reviewRepository.findByRating(rating);
    }

    public List<Review> getAllReviews() { //retrieves all reviews without any parameters
        return reviewRepository.findAllBookReviews();
    }











}
