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

@RestController //will handle HTTP requests -> separating the logic
@RequestMapping(path = "/api") // http://localhost:9092/api
public class BookController {
    /**
     *need to handle HTTP requests related to books.
     *BookController uses BookService to perform operations related to books.
     */

    private BookService bookService;

    @Autowired//going to inject BookService at runtime by the Spring
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }
    @GetMapping(path = "/books/") // http://localhost:9092/api/books/
    public List<Book> getBooks() {
        return bookService.getBooks();
    }
    @GetMapping(path = "/books/{bookId}")
    public Optional<Book> getBook(@PathVariable Long bookId) {
        return bookService.getBook(bookId);
    }



}
