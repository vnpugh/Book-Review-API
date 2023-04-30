package com.example.bookreview.controller;

import com.example.bookreview.exception.*;
import com.example.bookreview.model.Book;
import com.example.bookreview.model.Review;
import com.example.bookreview.model.User;
import com.example.bookreview.service.BookService;
import com.example.bookreview.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.example.bookreview.service.BookService.getCurrentLoggedInUser;


@RestController
@RequestMapping(path = "/api")
public class ReviewController {

    /**
     *to get the createBookReview method to work, I needed to inject (Autowire)
     ReviewService and BookService.
     */
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private BookService bookService;

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

    /**
     *retrieves book reviews by rating
     * @param rating
     * @return
     */
    @GetMapping("/reviews/{rating}")  //http://localhost:9092/api/reviews/{rating}/
    public List<Review> getReviewsByRating(@PathVariable("rating") double rating) {
        return reviewService.getReviewsByRating(rating);
    }

    @GetMapping("/reviews") //http://localhost:9092/api/reviews/
    public List<Review> getAllReviews() { //retrieves all reviews
        return reviewService.getAllReviews();
    }

    /**API endpoint to create a book review**
     *ResponseEntity -> wraps the response body to allow for more flexibility.
     *The code first checks if any required fields in the review object are missing or empty.
     *If any required fields are missing or empty -> return a bad request response (400 HTTP status code).
     *The validation check (on the review object) for required fields checks for empty strings & null values.
     *The code checks if the user is logged in; if user is not logged in -> 401 Unauthorized error returned.
     *The code checks if the Optional returned by bookService.getBookById() is empty -> 404 Not Found error returned if it is.
     *Added an exception to handle error on the server side that might be thrown when the review is saved -> 500 Internal
      Server Error returned.
     *HTTP Status codes: https://developer.mozilla.org/en-US/docs/Web/HTTP/Status
     * @param bookId
     * @param reviewObject
     * @return
     */






















    @PostMapping("/books/{bookId}/reviews")
    public ResponseEntity<Review> createBookReview(@PathVariable Long bookId, @RequestBody Review reviewObject) {
        //check if the required fields are missing or empty.
        if (reviewObject.getUsername() == null ||
                reviewObject.getUsername().isEmpty() ||
                reviewObject.getTitle() == null ||
                reviewObject.getTitle().isEmpty() ||
                reviewObject.getAuthor() == null ||
                reviewObject.getAuthor().isEmpty() ||
                reviewObject.getGenre() == null ||
                reviewObject.getGenre().isEmpty() ||
                reviewObject.getRating() == null ||
                reviewObject.getComment() == null ||
                reviewObject.getComment().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        //sets the review date to the current date if it's not entered by user
        if (reviewObject.getReviewDate() == null) {
            reviewObject.setReviewDate(LocalDate.now());
        }
        //get the current logged in user
        User user = reviewService.getCurrentLoggedInUser();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        //retrieve the book by ID
        Optional<Book> book = bookService.getBookById(bookId);
        //check if the book exists before user creates the review
        if (book.isPresent()) {
            //add the book review and set the user who wrote the review
            reviewObject.setUser(user);
            book.get().addReview(reviewObject);
            try {
                bookService.saveBook(book.get());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
            return ResponseEntity.ok(reviewObject); //the review has been added successfully (HTTP 200)
        } else {
            return ResponseEntity.notFound().build(); //404 Not Found
        }
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


    @PutMapping(path = "/users/{userId}/")
    public User updateUser(@PathVariable Long userId, @RequestBody User userObject) throws UserNotFoundException,
            UserNotLoggedInException {
        return reviewService.updateUser(userId, userObject);
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


