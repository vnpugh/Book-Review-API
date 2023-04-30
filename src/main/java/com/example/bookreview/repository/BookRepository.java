package com.example.bookreview.repository;

import com.example.bookreview.model.Book;
import com.example.bookreview.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
 /**
  *custom methods used to query the database for book information.
  *ignore case added -> the search will ignore case sensitivity.
  */
 List<Book> findByUserId(Long id); //finds all books associated with a specific user ID.
 Book findByIdAndUserId(Long bookId, Long id); //finds a specific book associated with a specific user ID.

 Optional<Book> findById(Long bookId);
 List<Book> findByBestSeller(boolean b);

 List<Review> findByBookId(Long bookId);



 /**
 List<BestSeller> findByUserId(Long id); //finds all books associated with a specific user ID.
 List<BestSeller> findByAuthorIgnoreCase(String author);//finds bestsellers written by a specific author.
 List<BestSeller> findByTitleIgnoreCase(String title); //finds bestsellers with a specific title.
 List<BestSeller> findByGenreIgnoreCase(String genre); //finds bestsellers with a specific genre.
 List<BestSeller> findByWeeks(Integer weeks); //finds bestsellers by # of weeks on bestsellers list.
 List<BestSeller> findByRating(Double rating); //finds bestsellers with a specific rating.
 List<BestSeller> findBySales(Integer sales); //finds bestsellers based on sales. *


/**
 List<Book> findByAuthorIgnoreCase(String author);//finds all books written by a specific author.
 List<Book> findByTitleIgnoreCase(String title); //finds all books with a specific title.
 List<Book> findByGenreIgnoreCase(String genre); //finds all books with a specific genre.
 List<Book> findByYearPublishedIgnoreCase(Integer yearPublished); //finds all books published in a specific year.
 List<Book> findByRatingIgnoreCase(Double rating); //finds all books with a specific rating.
 List<Book> findByIsbnIgnoreCase(String isbn); //finds all books with a specific ISBN.*/


}
