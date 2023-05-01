package com.example.bookreview.service;

import com.example.bookreview.exception.*;
import com.example.bookreview.model.Review;
import com.example.bookreview.model.User;
import com.example.bookreview.repository.BookRepository;
import com.example.bookreview.repository.ReviewRepository;
import com.example.bookreview.repository.UserRepository;
import com.example.bookreview.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

    public List<Review> getReviewsByRating(double rating) {
        return reviewRepository.findByRating(rating);
    }


    /**
     * could not implement createBookReview method, but I created the POST Mapping in the ReviewController.
     * @throws UserNotLoggedInException
     * @throws FailedToSaveReviewException
     * @throws MissingFieldsException
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





    public Review updateReview(Long reviewId, Review reviewObject) throws ReviewNotFoundException, UnauthorizedUserException, UserNotLoggedInException {
        // Check if user is logged in
        User user = getCurrentLoggedInUser();
        if (user == null) {
            throw new UserNotLoggedInException("User is not logged in to update book review.");
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
            review.setReviewText(reviewObject.getReviewText());
            review.setReviewDate(LocalDate.now());

            return reviewRepository.save(review);
        } else {
            throw new ReviewNotFoundException("Review not found.");
        }
    }

//update user account information

    public User updateUser(Long userId, User userObject) throws UserNotLoggedInException, UserNotFoundException {
        User user = getCurrentLoggedInUser();
        if (user == null) {
            throw new UserNotLoggedInException("User is not logged in to update account profile.");
        }
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User userToUpdate = optionalUser.get();
            userToUpdate.setUserName(userObject.getUserName());
            userToUpdate.setEmailAddress(userObject.getEmailAddress());
            userToUpdate.setPassword(userObject.getPassword());
            return userRepository.save(userToUpdate);
        } else {
            throw new UserNotFoundException("User not found.");
        }
    }



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

    public Review getReviewById(Long reviewId) throws ReviewNotFoundException {
Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if (optionalReview.isPresent()) {
            return optionalReview.get();
        } else {
            throw new ReviewNotFoundException("Review not found.");
        }
    }
}








