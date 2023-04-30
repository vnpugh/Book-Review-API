package com.example.bookreview.repository;

/**
import com.example.bookreview.model.Book;
import com.example.bookreview.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository //JpaRepo provides basic CRUD functionality
public interface ReviewRepository extends JpaRepository<Review, Long> {

    void createReview(Review review);
    List<Review> findByUserId(Long userId);
    List<Review> findByBookId(Long bookId);
    List<Review> findByRating (Double rating);

    List<Review> findAllBookReviews();
    List<Review> findByReviewDate(LocalDate reviewDate);
    void saveBook(Book book);

    void deleteByUserIdAndReviewId(Long id, Long reviewId);
}
*/