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

@Service
public class BookService {

private BookRepository bookRepository;
private BestSellerRepository bestSellerRepository;

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Autowired
    public void setBestSellerRepository(BestSellerRepository bestSellerRepository) {
        this.bestSellerRepository = bestSellerRepository;
    }

    //ADD User getCurrentLoggedInUser() Method later
    public static User getCurrentLoggedInUser() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        return userDetails.getUser();
    }


    public List<Book> getBooks() {
        List<Book> books = bookRepository.findBookByIsbn(BookService.getCurrentLoggedInUser().getId());
        if (books.isEmpty()) {
            throw new InformationNotFoundException
                    ("no books found for isbn " + BookService.getCurrentLoggedInUser().getId());
        } else {
            return books;
        }
    }




}
