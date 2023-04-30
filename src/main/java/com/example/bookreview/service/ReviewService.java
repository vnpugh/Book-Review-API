package com.example.bookreview.service;


import com.example.bookreview.exception.InformationNotFoundException;
import com.example.bookreview.model.Book;
import com.example.bookreview.model.Review;
import com.example.bookreview.model.User;
import com.example.bookreview.repository.BookRepository;
import com.example.bookreview.repository.ReviewRepository;
import com.example.bookreview.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private ReviewRepository reviewRepository;
    @Autowired //the component can use the ReviewRepository to perform database operations
    public void setReviewRepository(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }
    public static User getCurrentLoggedInUser() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return userDetails.getUser();
    }
    public List<Review> getReviews() {
        long userId = ReviewService.getCurrentLoggedInUser().getId();
        List<Review> reviews = reviewRepository.findByUserId(userId);
        if (reviews.isEmpty()) {
            throw new InformationNotFoundException("no reviews found for user id " + userId);
        }
        return reviews;
    }
    public List<Review> getReviewsByBookId(Long bookId) {
        return reviewRepository.findByBookId(bookId);
    }
    
    public List<Review> getReviewsByRating(double rating) {
        return reviewRepository.findByRating(rating);
    }
}
