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


    /**
     * creating a broad search method that allows the user to specify different parameters.
     * get all books from bookRepository, then filter the list of books based on the search parameters.
     *throws statement in the method signature for this method since it is not expected to throw any checked exceptions. However,
     * you can choose to catch and handle any potential exceptions internally within the method if needed.
     * includes a try-catch block to handle any potential exceptions thrown by the bookRepository methods:
     * In this implementation, if any exceptions are thrown by the bookRepository methods, the catch block will catch them
     * and re-throw a new exception with a custom message that includes the original exception message. This can help with
     * debugging and identifying the root cause of any errors that occur.
     * @param author
     * @param title
     * @param genre
     * @param yearPublished
     * @return books
     */
    public List<Book> searchBooks(String author, String title, String genre, Integer yearPublished, String isbn, Double rating) throws Exception {
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
        } catch (Exception e) {
            throw new Exception("an error occurred while searching for books: " + e.getMessage());
        }

        return books;
    }
}
