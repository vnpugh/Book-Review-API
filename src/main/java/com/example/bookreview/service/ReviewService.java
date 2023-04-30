package com.example.bookreview.service;


import com.example.bookreview.exception.*;
import com.example.bookreview.model.Book;
import com.example.bookreview.model.Review;
import com.example.bookreview.model.User;
import com.example.bookreview.repository.BookRepository;
import com.example.bookreview.repository.ReviewRepository;
import com.example.bookreview.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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




    public List<Review> getReviewsByBookId(Long bookId) {return reviewRepository.findByBookId(bookId);}

    public List<Review> getReviewsByRating(double rating) {
        return reviewRepository.findByRating(rating);
    }

    public List<Review> getAllReviews() { //retrieves all reviews without any parameters
        return reviewRepository.findAllBookReviews();
    }



    public Review createBookReview(Long bookId, Review reviewObject, BookRepository bookRepository) throws UserNotLoggedInException,
            FailedToSaveReviewException, BookNotFoundException {
        if ((reviewObject.getUsername() == null) ||
                reviewObject.getUsername().isEmpty() ||
                (reviewObject.getTitle() == null) ||
                reviewObject.getTitle().isEmpty() ||
                (reviewObject.getAuthor() == null) ||
                reviewObject.getAuthor().isEmpty() ||
                (reviewObject.getGenre() == null) ||
                reviewObject.getGenre().isEmpty() ||
                (reviewObject.getRating() == null) ||
                (reviewObject.getComment() == null) ||
                reviewObject.getComment().isEmpty()) {
            throw new MissingFieldsException("Missing the required fields to create a new book review.");
        }
        if (reviewObject.getReviewDate() == null) {
            reviewObject.setReviewDate(LocalDate.now());
        }
        //checks if user is logged in
        User user = getCurrentLoggedInUser();
        if (user == null) {
            throw new UserNotLoggedInException("User is not logged in to create book review.");
        }
        Optional<Book> book = bookRepository.findById(bookId);
        // check if the book exists before user creates the review
        if (book.isPresent()) {
            // add the book review and set the user who wrote the review
            reviewObject.setUser(user);
            book.get().addReview(reviewObject);
            try {
                bookRepository.saveBook(book.get());
            } catch (Exception e) {
                throw new FailedToSaveReviewException("Failed to save book review.");
            }
            return reviewObject; // the review has been added successfully
        } else {
            throw new BookNotFoundException("Book not found.");
        }
    }



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
                review.setGenre(reviewObject.getGenre());
                review.setRating(reviewObject.getRating());
                review.setComment(reviewObject.getComment());
                review.setReviewDate(LocalDate.now());

                return reviewRepository.save(review);
            } else {
                throw new ReviewNotFoundException("Review not found.");
            }
        }


    }

