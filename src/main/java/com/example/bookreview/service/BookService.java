package com.example.bookreview.service;

import com.example.bookreview.exception.InformationNotFoundException;
import com.example.bookreview.model.Book;
import com.example.bookreview.model.User;
import com.example.bookreview.repository.BookRepository;
import com.example.bookreview.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private BookRepository bookRepository;

    @Autowired //the component can use the BookRepository to perform database operations
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     *using SecurityContextHolder to return the user details.
     *authentication object = the user's authentication info (username and roles).
     *principal property = the user's identity.
     *principal is cast (converted) to an instance of MyUserDetails -> this is needed so
     that the code can access the methods in MyUserDetails.
     * @return
     */
    public static User getCurrentLoggedInUser() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        return userDetails.getUser();
    }

    /**
     *retrieves all books associated with the currently logged-in user.
     *then uses the bookRepository to find all books associated with the user by calling findByUserId().
     *exception thrown if no books found for the user id.
     * @return books
     */
    public List<Book> getBooks() {
        long userId = BookService.getCurrentLoggedInUser().getId();
        List<Book> books = bookRepository.findByUserId(userId);
        if (books.isEmpty()) {
            throw new InformationNotFoundException("no books found for user id " + userId);
        }
        return books;
    }

    /**
     *retrieves a specific book from the book repository based on book id and the current logged-in user.
     *the findByIdAndUserId() method is called in the bookRepository object.
     *exception thrown if a specific book is not found for the user id.
     * @param bookId
     * @return Optional
     */
    public Optional<Book> getBookById(Long bookId) {
        long userId = BookService.getCurrentLoggedInUser().getId();
        Book book = bookRepository.findByIdAndUserId(bookId, userId);
        if (book == null) {
            throw new InformationNotFoundException("book with id " + bookId + " is not found");
        }
        return Optional.of(book);
    }

    /**
     *retrieves a list of books based on the provided search parameters (author, title, genre, year
      published, ISBN, and rating) using the BookRepository.
     *if a parameter is not null, the appropriate BookRepository method is called to retrieve the books
      matching the search criteria.
     *if a DataAccessException is thrown during the search, it will be rethrown by the method -> instead
      of using Information Exception, or try/catch.
     *an empty List is returned if no books are found for the provided search.
     * @param author
     * @param title
     * @param genre
     * @param yearPublished
     * @param isbn
     * @param rating
     * @return books
     */
    public List<Book> searchBooks(String author, String title, String genre, Integer yearPublished, String isbn, Double rating) {
        List<Book> books = getBooks();

        try {
            if (author != null) {   //ignore case added -> the search will ignore case sensitivity.
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

