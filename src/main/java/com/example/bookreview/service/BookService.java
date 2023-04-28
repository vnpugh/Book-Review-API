package com.example.bookreview.service;

import com.example.bookreview.model.BestSeller;
import com.example.bookreview.model.Book;
import com.example.bookreview.repository.BestSellerRepository;
import com.example.bookreview.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

private BookRepository bookRepository;




    public List<Book> getBooks() {
    }
}
