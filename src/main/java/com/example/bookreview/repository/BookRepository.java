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
 //List<Book> findByUserId(Long id); //finds all books associated with a specific user ID. -> maybe move to user
 Book findByIdAndUserId(Long bookId); //finds a specific book associated with a specific user ID.

 Optional<Book> findById(Long bookId);

 List<Book> findByUserId(long userId);
 //List<Book> findByBestSeller(boolean b);







}
