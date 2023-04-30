package com.example.bookreview.repository;


import com.example.bookreview.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository //JpaRepo provides basic CRUD functionality
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByUserId(long userId);
    List<Review> findByBookId(Long bookId);
    //retrieve a list of reviews that match a given rating
    List<Review> findByRating (Double rating);

    //view a list of all book reviews
    //List<Review> findAllBookReviews(Long book_id);

    //reviews that match a specified review date and genre
    Review findReviewsByReviewDateAndGenre(LocalDate reviewDate, String genre);



}
