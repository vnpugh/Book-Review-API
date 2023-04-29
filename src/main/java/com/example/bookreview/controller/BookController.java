package com.example.bookreview.controller;


import com.example.bookreview.model.Book;
import com.example.bookreview.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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
    @GetMapping(path = "/books/") // http://localhost:9092/api/books/
    public List<Book> getBooks() {//retrieve a list of books 
        return bookService.getBooks();
    }
    @GetMapping(path = "/books/{bookId}") //retrieve the book with the given bookId
    public Optional<Book> getBookById(@PathVariable Long bookId) {

        return bookService.getBookById(bookId);
    }
    @GetMapping(path = "/books/isbn/{isbn}") //retrieve the book with the given isbn
    public Optional<Book> getBookByIsbn(@PathVariable Long isbn) {
        return bookService.getBookByIsbn(isbn);
    }


}
