package com.example.bookreview.service;

import com.example.bookreview.exception.*;
import com.example.bookreview.model.Review;
import com.example.bookreview.model.User;
import com.example.bookreview.repository.ReviewRepository;
import com.example.bookreview.repository.UserRepository;
import com.example.bookreview.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;



@Service
public class ReviewService {

    private ReviewRepository reviewRepository;
    private UserRepository userRepository;


    @Autowired //the component can use the ReviewRepository to perform database operations
    public void setReviewRepository(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    /**
     * method to get the current logged-in user.
     * MyUserDetails is a class that implements UserDetails interface.
     * SecurityContextHolder is a class that provides access to the security context.
     * SecurityContext is an interface that represents the security context.
     * Authentication is an interface that represents the authentication object.
     * getPrincipal() returns the current logged-in user.
     */

    public static User getCurrentLoggedInUser() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return userDetails.getUser();

    }

    /**
     * method to get a list of reviews by logged-in users with specified ids.
     * method checks for a null value for the userId parameter -> ensures that
       the method won't try to execute a query with a null parameter.
     * if null, thrown an IllegalArgumentException.
     * @param userId
     * @return
     */
    public List<Review> getReviewsByUser(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        List<Review> reviews = reviewRepository.findByUserId(userId);
        if (reviews.isEmpty()) {
            throw new InformationNotFoundException("no reviews found for user id " + userId);
        }
        return reviews;
    }


    /**
     * method to get a list of reviews by rating.
     */
    public List<Review> getReviewsByRating(double rating) {
        return reviewRepository.findByRating(rating);
    }

    /**
     * method to handle the logic of creating a new review.
     * method checks for a null value for the reviewObject parameter -> ensures that
       the method won't try to execute a query with a null parameter.
     * @throws ReviewNotFoundException
     */
    public Review getReviewById(Long reviewId) throws ReviewNotFoundException {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if (optionalReview.isPresent()) {
            return optionalReview.get();
        } else {
            throw new ReviewNotFoundException("Review not found.");
        }
    }



    /**
     * could not implement createBookReview method, but I created the POST Mapping in the ReviewController.
     */
    /**
    public Review createBookReview(Review reviewObject) throws UserNotLoggedInException,
            FailedToSaveReviewException, MissingFieldsException {
        if ((reviewObject.getUserName() == null) ||
                reviewObject.getUserName().isEmpty() ||
                (reviewObject.getTitle() == null) ||
                reviewObject.getTitle().isEmpty() ||
                (reviewObject.getAuthor() == null) ||
                reviewObject.getAuthor().isEmpty() ||
                (reviewObject.getReviewDate() == null) ||
                (reviewObject.getReviewText() == null)) {
            throw new MissingFieldsException("Missing the required fields to create a new review.");
        }
        if (reviewObject.getReviewDate() == null) {
            reviewObject.setReviewDate(LocalDate.now());
        }
        //checks if user is logged-in
        User user = getCurrentLoggedInUser();
        if (user == null) {
            throw new UserNotLoggedInException("User is not logged in to create review.");
        }
        reviewObject.setUser(user);
        try {
            return reviewRepository.save(reviewObject);
        } catch (DataAccessException e) {
            throw new FailedToSaveReviewException("Failed to save review.");
        }
    } */


    /**
     * method to handle the logic of updating a book review.
     * method first checks if the user is logged in, then retrieves the review by ID from the repository.
     * If the review is found, checks if the logged-in user is the author of the review.
     * If the user is authorized, the review will be updated with the new data and saves it to the repository.
     * If the review is not found, ReviewNotFoundException thrown.
     */
    public Review updateReview(Long reviewId, Review reviewObject) throws ReviewNotFoundException,
            UnauthorizedUserException, UserNotLoggedInException {
        // Check if user is logged in
        User user = getCurrentLoggedInUser();
        if (user == null) {
            throw new UserNotLoggedInException("User is not logged in to update a review.");
        }

        // Retrieve review from the repository
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if (optionalReview.isPresent()) {
            Review review = optionalReview.get();

            // Check if the logged-in user is the author of the review
            if (review.getUser().getId() != user.getId()) {
                throw new UnauthorizedUserException("User is not authorized to update this book review.");
            }

            // Update the review
            review.setTitle(reviewObject.getTitle());
            review.setAuthor(reviewObject.getAuthor());
            review.setReviewText(reviewObject.getReviewText());
            review.setRating(reviewObject.getRating());
            review.setReviewDate(LocalDate.now());

            return reviewRepository.save(review);
        } else {
            throw new ReviewNotFoundException("Review not found.");
        }
    }


    /**
     * method to handle the logic of deleting a book review.
     * method first checks if the user is logged in, then retrieves the review by ID from the repository.
     * If the review is found, checks if the logged-in user is the author of the review.
     * If the user is authorized, the review will be deleted from the repository.
     * If the review is not found, ReviewNotFoundException thrown.
     * If the user is not authorized, UnauthorizedUserException thrown.
     */
    public void deleteReviewByUser(Long reviewId) throws UserNotLoggedInException, ReviewNotFoundException {
        User user = getCurrentLoggedInUser();
        if (user == null) {
            throw new UserNotLoggedInException("User is not logged in.");
        }
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review == null) {
            throw new ReviewNotFoundException("Review not found.");
        }
        if (!user.getUserName().equals(review.getUser().getUserName())) {
            throw new IllegalArgumentException("Username does not match logged in user.");
        }
        reviewRepository.delete(review);
    }


}








