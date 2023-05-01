package com.example.bookreview.repository;


import com.example.bookreview.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository //JpaRepo provides basic CRUD functionality
public interface ReviewRepository extends JpaRepository<Review, Long> {

    //void createBookReview(Review review);
    List<Review> findByUserId(Long userId); //method to query the reviews by the user id and the rating.
    List<Review> findByRating (Double rating); //method to query the reviews by the rating.

    //void deleteReviewByUser(Long reviewId); //could be used in the future to delete a review by the user id.
}
