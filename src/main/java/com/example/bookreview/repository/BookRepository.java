package com.example.bookreview.repository;

import com.example.bookreview.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
 /**
  *custom methods used to query the database for book information.
  *ignore case added -> the search will ignore case sensitivity.
  */
 List<Book> findByUserId(Long id); //finds all books associated with a specific user ID.
 Book findByIdAndUserId(Long bookId, Long id); //finds a specific book associated with a specific user ID.
 List<Book> findByAuthorIgnoreCase(String author);//finds all books written by a specific author.
 List<Book> findByTitleIgnoreCase(String title); //finds all books with a specific title.
 List<Book> findByGenreIgnoreCase(String genre); //finds all books with a specific genre.
 List<Book> findByYearPublishedIgnoreCase(Integer yearPublished); //finds all books published in a specific year.
 List<Book> findByRatingIgnoreCase(Double rating); //finds all books with a specific rating.
 List<Book> findByIsbnIgnoreCase(String isbn); //finds all books with a specific ISBN.


}
