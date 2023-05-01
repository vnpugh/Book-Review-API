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
import static com.example.bookreview.service.ReviewService.getCurrentLoggedInUser;


@RestController
@RequestMapping(path = "/api")
public class ReviewController {

    /**
     * The @Autowired annotation tells Spring where an injection needs to occur.
     * In this case, we are injecting the ReviewService, BookService, BookRepo, and
       ReviewRepo into the ReviewController -> dependency injection -> completed at runtime.
     * Spring will create an instance of the ReviewService and inject it into the ReviewController.
     */
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

    /**
     * method to get all reviews from a user.
     */
    @GetMapping("/users/{userId}/reviews")
    public List<Review> getReviewsByUser(@PathVariable Long userId) {
        return reviewService.getReviewsByUser(userId);
    }

    /**
     * method to get all reviews by book rating.
     */
    @GetMapping("/reviews/{rating}")  //http://localhost:9092/api/reviews/{rating}/
    public List<Review> getReviewsByRating(@PathVariable("rating") double rating) {
        return reviewService.getReviewsByRating(rating);
    }

    /**
     * method to create a review for a book.
     * I was able to create the method and the POST mapping, but I had some trouble implementing
       this method in the ReviewService layer.
     * The purpose is to allow a user to create a review for a book with the specified fields.
       and checks if the required fields are missing or empty.
     * If the required fields are missing or empty, the method will return a bad request.
     * If the required fields are NOT missing or empty, the method will set the review date to
       the current date if it's not entered by the user.
     * The method will get the current logged-in user, and the book by the book id.
     * The method will set, save and return the book review
     * a response entity will be returned with the review object and a status of 201.
     * */

/**
    @PostMapping("/books/{bookId}/reviews")
    public ResponseEntity<Review> createBookReview(@PathVariable Long bookId, @RequestBody Review reviewObject) {
        //check if the required fields are missing or empty.
        if (reviewObject.getUserName() == null ||
                reviewObject.getUserName().isEmpty() ||
                reviewObject.getTitle() == null ||
                reviewObject.getTitle().isEmpty() ||
                reviewObject.getAuthor() == null ||
                reviewObject.getAuthor().isEmpty() ||
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
        Review review = new Review(null, reviewObject.getUserName(),
                reviewObject.getAuthor(),
                reviewObject.getTitle(),
                reviewObject.getReviewDate(),
                reviewObject.getReviewText(), book);
        //set the user for the review
        review.setUser(user);
        //save the review to the database
        reviewRepository.save(review);
        return ResponseEntity.ok(review);
    } */


    /**
     * method to update a review for a book.
     * I was able to create the method and the PUT mapping.
     * The purpose is to allow a user to update a review for a book with the specified fields.
     * The method will get the current logged-in user, and the book by the book id.
     * The method will set, save and return the book review.
     * exception handling for UserNotLoggedInException, ReviewNotFoundException, and
       UnauthorizedUserException.
     * @param reviewId
     * @param reviewObject
     */
    @PutMapping(path = "/reviews/{reviewId}/")
    public Review updateReview(@PathVariable Long reviewId, @RequestBody Review reviewObject)
            throws UserNotLoggedInException, ReviewNotFoundException, UnauthorizedUserException {
        User user = getCurrentLoggedInUser();
        if (user == null) {
            throw new UserNotLoggedInException("User is not logged in to update a review.");
        }
        Review review = reviewService.getReviewById(reviewId);
        if (review == null) {
            throw new ReviewNotFoundException("Review not found with id " + reviewId);
        }
        if (!review.getUser().equals(user)) {
            throw new UnauthorizedUserException("User is not authorized to update this review.");
        }
        return reviewService.updateReview(reviewId, reviewObject);
    }




    //user can delete a review posted by them

    /**
     * method to delete a book review by a user.
     * I was able to create the method and the DELETE mapping.
     * The purpose is to allow a user to delete a review for a book with the specified fields.
     * The method first checks if the user is authorized to delete the review by comparing the
       user id of the logged-in user with the user id of the review. If there's no match,then
       the user is not authorized to delete the review -> UnauthorizedUserException is thrown.
     * exception handling also for UserNotLoggedInException & ReviewNotFoundException.
     */
    @DeleteMapping(path = "/reviews/{reviewId}/")
    public void deleteReviewByUser(@PathVariable Long reviewId) throws UserNotLoggedInException,
            ReviewNotFoundException, UnauthorizedUserException {
        User user = getCurrentLoggedInUser();
        if (user == null) {
            throw new UserNotLoggedInException("User must be logged in to delete a book review.");
        }

        Review review = reviewService.getReviewById(reviewId);
        if(review == null) {
            throw new ReviewNotFoundException("Book Review not found with id " + reviewId);
        }

        if (!user.getId().equals(review.getUser().getId())) {
            throw new UnauthorizedUserException("User is not authorized to delete this review.");
        }

        reviewService.deleteReviewByUser(reviewId);
    }



}


