package com.example.bookreview.service;

import com.example.bookreview.exception.InformationNotFoundException;
import com.example.bookreview.model.Book;
import com.example.bookreview.model.User;
import com.example.bookreview.repository.BookRepository;
import com.example.bookreview.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

private BookRepository bookRepository;


    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    //ADD User getCurrentLoggedInUser() Method later
    public static User getCurrentLoggedInUser() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        return userDetails.getUser();
    }
    
    public List<Book> getBooks() {//retrieves a list of books from a book repository based on the current logged-in user's ID.
        long userId = BookService.getCurrentLoggedInUser().getId();
        List<Book> books = bookRepository.findByUserId(userId);
        if (books.isEmpty()) {
            throw new InformationNotFoundException("no books found for user id " + userId);
        }
        return books;
    }

    //retrieves a book from a book repository based on a given book ID
    //and the current logged-in user's ID.
    public Optional<Book> getBookById(Long bookId) {
        long userId = BookService.getCurrentLoggedInUser().getId();
        Book book = bookRepository.findByIdAndUserId(bookId, userId);
        if (book == null) {
            throw new InformationNotFoundException("book with id " + bookId + " is not found");
        }
        return Optional.of(book);
    }

    public Optional<Book> getBookByIsbn(Long isbn) {
        Optional<Book> book = bookRepository.findByIsbn(isbn);
        if (!book.isPresent()) {
            throw new InformationNotFoundException("book not found with ISBN " + isbn);
        }
        return book;
    }




















}
