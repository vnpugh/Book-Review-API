package com.example.bookreview.controller;


import com.example.bookreview.model.BestSeller;
import com.example.bookreview.model.Book;
import com.example.bookreview.repository.BookRepository;
import com.example.bookreview.service.BestSellerService;
import com.example.bookreview.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController //will handle HTTP requests -> separating the logic
@RequestMapping(path = "/api") // http://localhost:9092/api
public class BookController {
    /**
     *need to handle HTTP requests related to books.
     *BookController uses BookService to perform operations related to books.
     */

    private BookService bookService;
    private BestSellerService bestSellerService;


    @Autowired//going to inject BookService at runtime by the Spring
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Autowired
    public void setBestSellerService(BestSellerService bestSellerService) {
        this.bestSellerService = bestSellerService;
    }





    @GetMapping(path = "/books/") // http://localhost:9092/api/books/
    public List<Book> getBooks() {
        return bookService.getBooks();
    }
    @GetMapping(path = "/bestsellers/") // http://localhost:9092/api/books/
    public List<BestSeller> getBestSellers() {
        return bestSellerService.getAllBestSellers();
    }

}
