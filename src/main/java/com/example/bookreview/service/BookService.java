package com.example.bookreview.service;

import com.example.bookreview.exception.InformationNotFoundException;
import com.example.bookreview.model.BestSeller;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BookService {
    private BookRepository bookRepository;

    @Autowired //the component can use the BookRepository to perform database operations
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * using SecurityContextHolder to return the user details.
     * authentication object = the user's authentication info (username and roles).
     * principal property = the user's identity.
     * principal is cast (converted) to an instance of MyUserDetails -> this is needed so
     * that the code can access the methods in MyUserDetails.
     *
     * @return
     */
    public static User getCurrentLoggedInUser() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return userDetails.getUser();
    }

    /**
     * retrieves all books associated with the currently logged-in user.
     * then uses the bookRepository to find all books associated with the user by calling findByUserId().
     * exception thrown if no books found for the user id.
     *
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
     * retrieves a specific book from the book repository based on book id and the current logged-in user.
     * the findByIdAndUserId() method is called in the bookRepository object.
     * exception thrown if a specific book is not found for the user id.
     *
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


    public List<Book> searchBooks(String author, String title, String genre, Integer yearPublished, String isbn, Double rating) {
        Stream<Book> booksStream = getBooks().stream();

        if (author != null) {
            booksStream = booksStream.filter(book -> book.getAuthor().equalsIgnoreCase(author));
        }
        if (title != null) {
            booksStream = booksStream.filter(book -> book.getTitle().equalsIgnoreCase(title));
        }
        if (genre != null) {
            booksStream = booksStream.filter(book -> book.getGenre().equalsIgnoreCase(genre));
        }
        if (yearPublished != null) {
            booksStream = booksStream.filter(book -> book.getYearPublished().equals(yearPublished));
        }
        if (rating != null) {
            double tolerance = 0.001;
            booksStream = booksStream.filter(book -> Math.abs(book.getRating() - rating) < tolerance);
        }
        if (isbn != null) {
            booksStream = booksStream.filter(book -> book.getIsbn().equals(isbn));
        }

        return booksStream.collect(Collectors.toList());
    }
}



