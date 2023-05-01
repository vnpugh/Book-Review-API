package com.example.bookreview.service;


import com.example.bookreview.exception.InformationNotFoundException;
import com.example.bookreview.model.Book;
import com.example.bookreview.repository.AuthorRepository;

import com.example.bookreview.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Autowired
    public void setAuthorRepository(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    /**
    public static User getCurrentLoggedInUser() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        return userDetails.getUser();
    }*/

    public List<Book> getBooks(String author, String title, String genre, Integer yearPublished, String isbn, Integer sales, Integer weeks, Double rating) {
        List<Book> books = bookRepository.findByAuthorAndTitleAndGenreAndYearPublishedAndIsbnAndSalesAndWeeksAndRating(author, title, genre, yearPublished, isbn, sales, weeks, rating);
        if (books.isEmpty()) {
            throw new InformationNotFoundException("no books found");
        } else {
            return books;
        }
    }


}