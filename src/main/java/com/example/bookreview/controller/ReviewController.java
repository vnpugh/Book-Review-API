package com.example.bookreview.controller;

import com.example.bookreview.exception.*;
import com.example.bookreview.model.Book;
import com.example.bookreview.model.Review;
import com.example.bookreview.model.User;
import com.example.bookreview.repository.BookRepository;
import com.example.bookreview.repository.ReviewRepository;
import com.example.bookreview.service.BookService;
import com.example.bookreview.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.example.bookreview.service.ReviewService.getCurrentLoggedInUser;


@RestController
@RequestMapping(path = "/api")
public class ReviewController {


    @Autowired
    private ReviewService reviewService;
    @Autowired
    private BookService bookService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired//going to inject ReviewService at runtime by the Spring
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping("/users/{userId}/reviews")
    public List<Review> getReviewsByUser(@PathVariable Long userId) {
        return reviewService.getReviewsByUser(userId);
    }

    @GetMapping("/reviews/")//http://localhost:9092/api/reviews/reviewDate/
    public List<Review> getReviewDate(@RequestParam("reviewDate")
            LocalDate reviewDate){
        List<Review> reviews = reviewService.getReviewsByReviewDate(reviewDate);
        if (reviews.isEmpty()) {
            throw new InformationNotFoundException("no reviews found for review date " + reviewDate);
        }
        return reviews;
    }


    @GetMapping("/reviews/{rating}")  //http://localhost:9092/api/reviews/{rating}/
    public List<Review> getReviewsByRating(@PathVariable("rating") double rating) {
        return reviewService.getReviewsByRating(rating);
    }


    @PostMapping("/books/{bookId}/reviews")
    public ResponseEntity<Review> createBookReview(@PathVariable Long bookId, @RequestBody Review reviewObject) {
        //check if the required fields are missing or empty.
        if (reviewObject.getUserName() == null ||
                reviewObject.getUserName().isEmpty() ||
                reviewObject.getTitle() == null ||
                reviewObject.getTitle().isEmpty() ||
                reviewObject.getAuthor() == null ||
                reviewObject.getAuthor().isEmpty() ||
                reviewObject.getReviewDate() == null ||
                Objects.isNull(reviewObject.getReviewDate()) ||
                reviewObject.getReviewText() == null ||
                reviewObject.getReviewText().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        //sets the review date to the current date if it's not entered by user
        if (reviewObject.getReviewDate() == null) {
            reviewObject.setReviewDate(LocalDate.now());
        }
        //get the current logged in user
        User user = getCurrentLoggedInUser();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        //retrieve the book by ID
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        //create a new review object
        Review review = new Review(null, reviewObject.getUserName(), reviewObject.getAuthor(),
                reviewObject.getTitle(), reviewObject.getReviewDate(), reviewObject.getReviewText(), book);
        //set the user for the review
        review.setUser(user);
        //save the review to the database
        reviewRepository.save(review);
        return ResponseEntity.ok(review);
    }


    @PutMapping(path = "/reviews/{reviewId}/")
    public Review updateReview(@PathVariable Long reviewId, @RequestBody Review reviewObject)
            throws UserNotLoggedInException, ReviewNotFoundException, UnauthorizedUserException {
        User user = getCurrentLoggedInUser();
        if (user == null) {
            throw new UserNotLoggedInException("User is not logged in.");
        }
        return reviewService.updateReview(reviewId, reviewObject);
    }




    //user can delete a review posted by them
    @DeleteMapping(path = "/reviews/{reviewId}/")
    public void deleteReviewByUser(@PathVariable Long reviewId) throws UserNotLoggedInException, ReviewNotFoundException {
        User user = getCurrentLoggedInUser();
        if (user == null) {
            throw new UserNotLoggedInException("User is not logged in.");
        }
        reviewService.deleteReviewByUser(reviewId);
    }






}


