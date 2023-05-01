package com.example.bookreview.controller;


import com.example.bookreview.model.Book;
import com.example.bookreview.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

/**
 *Spring MVC REST Controller class that will handle HTTP request.
 *separating the business logic -> BookService to perform book operations.
 */

@RestController
@RequestMapping(path = "/api") // http://localhost:9092/api/
public class BookController {
    private BookService bookService;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    //@GetMapping("/books/sales")
   // public ResponseEntity<List<Book>> getBooksBySales() {
       // List<Book> books = bookService.getBooksBySales();
       // return ResponseEntity.ok(books);
    //}

    @GetMapping(path = "/books/search") //http://localhost:9092/api/books/search/
    public List<Book> getBooks(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Integer yearPublished,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) Integer sales,
            @RequestParam(required = false) Integer weeks,
            @RequestParam(required = false) Double rating) {

        return bookService.getBooks(author, title, genre, yearPublished, isbn, sales,
                weeks, rating);
    }
}

