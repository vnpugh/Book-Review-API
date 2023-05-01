package com.example.bookreview.repository;


import com.example.bookreview.model.Book;
import com.example.bookreview.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository //JpaRepo provides basic CRUD functionality
public interface ReviewRepository extends JpaRepository<Review, Long> {

    //void createBookReview(Review review);
    List<Review> findByUserId(Long userId);
    List<Review> findByRating (Double rating);

    List<Review> findByReviewDate(LocalDate reviewDate);




    void deleteReviewByUser(Long reviewId);
}
