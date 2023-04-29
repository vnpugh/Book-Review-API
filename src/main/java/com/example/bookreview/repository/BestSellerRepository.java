package com.example.bookreview.repository;

import com.example.bookreview.model.BestSeller;
import com.example.bookreview.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BestSellerRepository extends JpaRepository<BestSeller, Long> {
    /**
     *custom methods used to query the database for bestsellers information.
     *ignore case added -> the search will ignore case sensitivity.
     */
    List<BestSeller> findByUserId(Long id); //finds all books associated with a specific user ID.
    BestSeller findByIdAndUserId(Long bookId, Long id); //finds a specific book associated with a specific user ID.
    List<BestSeller> findByAuthorIgnoreCase(String author);//finds bestsellers written by a specific author.
    List<BestSeller> findByTitleIgnoreCase(String title); //finds bestsellers with a specific title.
    List<BestSeller> findByGenreIgnoreCase(String genre); //finds bestsellers with a specific genre.
    List<BestSeller> findByWeeks(Integer weeks); //finds bestsellers by # of weeks on bestsellers list.
    List<BestSeller> findByRating(Double rating); //finds bestsellers with a specific rating.
    List<BestSeller> findBySales(Integer sales); //finds bestsellers based on sales.


}
