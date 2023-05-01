package com.example.bookreview.repository;


import com.example.bookreview.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * custom methods used to query the database for book information.
     *
     * @return
     */
    List<Book> findByAuthorAndTitleAndGenreAndYearPublishedAndIsbnAndSalesAndWeeksAndRating
            (String author, String title, String genre, Integer yearPublished,
             String isbn, Integer sales, Integer weeks, Double rating);


}








