package com.example.bookreview.controller;

import com.example.bookreview.model.Book;
import com.example.bookreview.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *Spring MVC REST Controller class that will handle HTTP request.
 *separating the business logic -> BookService to perform book operations.
 */
@RestController
@RequestMapping(path = "/api") // http://localhost:9092/api
public class BookController {
    private BookService bookService;

    @Autowired//going to inject BookService at runtime by the Spring
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }
   //@GetMapping(path = "/books/") // http://localhost:9092/api/books/
   // public List<Book> getBooks() {//retrieve a list of books
     //   return bookService.getBooks();
   // }

    @GetMapping(path = "/books/{bookId}") //retrieve the book with the given bookId
    public Optional<Book> getBookById(@PathVariable Long bookId) {

        return bookService.getBookById(bookId);
    }

    @GetMapping(path = "/books/search") //retrieves books based on author, title, genre, or year published
    public List<Book> searchBooks(//allows the user to specify search parameters
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Integer yearPublished,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) Double rating) {

            List<Book> books = bookService.getBooks();

        //Using Stream to filter the list based on the search parameters:
        if (author != null) {
            books = books.stream().filter(book -> book.getAuthor().equalsIgnoreCase(author)).collect(Collectors.toList());
        }
        if (title != null) {
            books = books.stream().filter(book -> book.getTitle().equalsIgnoreCase(title)).collect(Collectors.toList());
        }
        if (genre != null) {
            books = books.stream().filter(book -> book.getGenre().equalsIgnoreCase(genre)).collect(Collectors.toList());
        }
        if (yearPublished != null) {
            books = books.stream().filter(book -> book.getYearPublished() == yearPublished).collect(Collectors.toList());
        }
        if (isbn!= null) {
            books = books.stream().filter(book -> book.getIsbn() == isbn).collect(Collectors.toList());
        }
        if (rating!= null) {
            books = books.stream().filter(book -> book.getRating() == rating).collect(Collectors.toList());
        }
        return books;
    }
}
