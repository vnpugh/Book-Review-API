package com.example.bookreview.service;


import com.example.bookreview.exception.BookNotFoundException;
import com.example.bookreview.exception.InformationNotFoundException;
import com.example.bookreview.model.Book;
import com.example.bookreview.model.User;
import com.example.bookreview.repository.AuthorRepository;
import com.example.bookreview.repository.BookRepository;
import com.example.bookreview.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * BooksService is responsible for handling the business logic of the application.
 * It is the middleman between the controller and the repository.
 * It is also responsible for handling the exceptions.
 */

@Service
public class BookService {

    /**
     * The @Autowired annotation is used to autowire bean on the setter method,
       constructor or a field. When we use @Autowired on setter method,
       Spring framework tries to find and inject the dependency on the setter method.
     */

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
     *  method retrieves the currently logged-in user's details from the SecurityContextHolder,
        and returns the associated User entity.
     */
    public static User getCurrentLoggedInUser() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        return userDetails.getUser();
    }

    /**
     * I wanted to create a method that includes search criteria for the books.
     * I used the findBy method from the repository to search for the books.
     * If the books are not found, an BooksNotFoundException will be thrown.
     */

    public List<Book> getBooks(String author, String title, String genre, Integer yearPublished,
                               String isbn, Integer sales, Integer weeks, Double rating) throws
                               BookNotFoundException {
        List<Book> books = bookRepository.findByAuthorAndTitleAndGenreAndYearPublishedAndIsbnAndSalesAndWeeksAndRating
                (author, title, genre, yearPublished, isbn, sales, weeks, rating);
        if (books.isEmpty()) {
            throw new BookNotFoundException("no books found by the given criteria");
        } else {
            return books;
        }
    }


}