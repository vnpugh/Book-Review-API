package com.example.bookreview.service;

import com.example.bookreview.exception.InformationNotFoundException;
import com.example.bookreview.model.Book;
import com.example.bookreview.model.Review;
import com.example.bookreview.model.User;
import com.example.bookreview.repository.BookRepository;
import com.example.bookreview.repository.ReviewRepository;
import com.example.bookreview.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class BookService {


    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ReviewRepository reviewRepository;




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
        List<Book> books = Collections.singletonList(bookRepository.findByIdAndUserId(userId));
        if (books.isEmpty()) {
            throw new InformationNotFoundException("no books found for user id " + userId);
        }
        return books;
    }




    public List<Book> searchBooks(String author, String title, String genre, Integer yearPublished, String isbn, Integer sales, Integer weeks, Boolean bestSeller, Double rating) {
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

    //get book reviews
    public List<Review> getBookReviews(Long bookId) { return reviewRepository.findByBookId(bookId);
    }

    public void saveBook(Book book) {
        bookRepository.save(book);
    }

}



