package com.example.bookreview.controller;

import com.example.bookreview.exception.InformationNotFoundException;
import com.example.bookreview.model.Book;
import com.example.bookreview.model.Review;
import com.example.bookreview.model.User;
import com.example.bookreview.service.BookService;
import com.example.bookreview.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "/api")
public class ReviewController {
    private ReviewService reviewService;
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

    /**
     *create a new review
     */

    @PostMapping("/books/{bookId}/reviews")
    public ResponseEntity<Review> createBookReview(@PathVariable Long bookId, @RequestBody Review reviewObject) {
        // Check if all required fields are provided by user
        if (reviewObject.getUsername() == null ||
                reviewObject.getTitle() == null ||
                reviewObject.getAuthor() == null ||
                reviewObject.getGenre() == null ||
                reviewObject.getRating() == null ||
                reviewObject.getComment() == null) {
            return ResponseEntity.badRequest().build();
        }

        // Set the review date to the current date if not provided
        if (reviewObject.getReviewDate() == null) {
            reviewObject.setReviewDate(LocalDate.now());
        }

        // Get the current logged in user
        User user = reviewService.getCurrentLoggedInUser();

        // Retrieve the book by ID
        Optional<Book> book = bookService.getBookById(bookId);

        // Check if the book exists before adding the review
        if (book.isPresent()) {
            // Add the review to the book and set the user who wrote the review
            reviewObject.setUser(user);
            book.get().addReview(reviewObject); //add addReview in Book
            bookService.saveBook(book.get()); //add saveBook in BookService
            return ResponseEntity.ok(reviewObject);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}


