package com.example.bookreview.service;

import com.example.bookreview.exception.InformationNotFoundException;
import com.example.bookreview.model.Book;
import com.example.bookreview.model.User;
import com.example.bookreview.repository.BookRepository;
import com.example.bookreview.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
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

    public static User getCurrentLoggedInUser() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        return userDetails.getUser();
    }

    public List<Book> getBooks() {
        long userId = BookService.getCurrentLoggedInUser().getId();
        List<Book> books = bookRepository.findByUserId(userId);
        if (books.isEmpty()) {
            throw new InformationNotFoundException("no books found for user id " + userId);
        }
        return books;
    }

    public Optional<Book> getBookById(Long bookId) {
        long userId = BookService.getCurrentLoggedInUser().getId();
        Book book = bookRepository.findByIdAndUserId(bookId, userId);
        if (book == null) {
            throw new InformationNotFoundException("book with id " + bookId + " is not found");
        }
        return Optional.of(book);
    }

    public List<Book> searchBooks(String author, String title, String genre, Integer yearPublished, String isbn, Double rating) {
        List<Book> books = getBooks();

        try {
            if (author != null) {
                books = bookRepository.findByAuthorIgnoreCase(author);
            }

            if (title != null) {
                books = bookRepository.findByTitleIgnoreCase(title);
            }

            if (genre != null) {
                books = bookRepository.findByGenreIgnoreCase(genre);
            }

            if (yearPublished != null) {
                books = bookRepository.findByYearPublishedIgnoreCase(yearPublished);
            }

            if (rating != null) {
                books = bookRepository.findByRatingIgnoreCase(rating);
            }

            if (isbn != null) {
                books = bookRepository.findByIsbnIgnoreCase(isbn);
            }
        } catch (DataAccessException e) {
            throw e; // just rethrow the original exception
        }

        return books;
    }
}

