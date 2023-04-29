package com.example.bookreview.service;

import com.example.bookreview.exception.InformationNotFoundException;
import com.example.bookreview.model.BestSeller;
import com.example.bookreview.model.Book;
import com.example.bookreview.model.User;
import com.example.bookreview.repository.BestSellerRepository;
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

    public Optional<Book> getBook(Long bookId) {
        Book book = bookRepository.findByIdAndUserId(bookId, BookService.getCurrentLoggedInUser()
                .getId());
        if (book == null) {
            throw new InformationNotFoundException("book with id " + bookId + " is not found");
        } else {
            return Optional.of(book);
        }
        }











    public List<Book> getBooksByIsbn() {//retrieves a list of books from a book repository based on the current logged-in user's ID.
        long userId = BookService.getCurrentLoggedInUser().getId();
        List<Book> books = bookRepository.findBookByIsbn(userId);
        if (books.isEmpty()) {
            throw new InformationNotFoundException("no books found with isbn for user " + userId);
        }
        return books;
    }


}
