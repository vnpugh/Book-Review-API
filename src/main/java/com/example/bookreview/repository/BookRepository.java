package com.example.bookreview.repository;

import com.example.bookreview.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

   //select book by title
    Book findBookByTitle(String title);

    //select book by genre
    Book findBookByGenre(String genre);

    //select book by author
    Book findBookByAuthor(String author);

    //select book by year published
     Book findBookByYearPublished(Integer yearPublished);

    //select book by isbn
     //Book findBookByIsbn(String isbn);
     List<Book> findBookByIsbn(Long id);

     //select book by a rating
     Book findBookByRating(Double rating);

     //returns a list of bestselling books filtered by author
     List<Book> findBooksByBestsellers(String author);


}
