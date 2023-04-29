package com.example.bookreview.repository;

import com.example.bookreview.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {


 List<Book> findByAuthorIgnoreCase(String author);
 List<Book> findByTitleIgnoreCase(String title);
 List<Book> findByGenreIgnoreCase(String genre);
 List<Book> findByYearPublishedIgnoreCase(Integer yearPublished);
 List<Book> findByRatingIgnoreCase(Double rating);
 List<Book> findByIsbnIgnoreCase(String isbn);



     List<Book> findByUserId(Long id);
     Book findByIdAndUserId(Long bookId, Long id);

     //returns a list of bestselling books filtered by author
     List<Book> findBooksByBestsellers(String author);


}
