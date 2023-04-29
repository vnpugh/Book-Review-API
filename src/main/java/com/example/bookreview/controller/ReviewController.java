package com.example.bookreview.controller;

import com.example.bookreview.model.Book;
import com.example.bookreview.model.Review;
import com.example.bookreview.service.BookService;
import com.example.bookreview.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class ReviewController {
    private ReviewService reviewService;
    @Autowired//going to inject ReviewService at runtime by the Spring
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping(path = "/reviews/{reviewId}/book") //http://localhost:9092/reviews/{reviewId}/book/
    public Optional<Book> getBookByReviewId(@PathVariable Long reviewId) {
        return reviewService.getBookByReviewID(reviewId)
                .map(Review::getBook);
    }













}
