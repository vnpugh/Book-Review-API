package com.example.bookreview.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bestsellers")
public class BestSeller {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String author;
    @Column
    private String genre;
    @Column
    private double rating;
    @Column
    private Integer weeks;
    @Column
    private Integer sales;

    /**
     *a bestseller can be associated with many books -> many-to-many.
     *need the join table to store the many-to-many relationship between the book and bestseller tables.
     *
     */

    @ManyToMany
    @JoinTable(
            name = "bestsellers_books",
            joinColumns = @JoinColumn(name = "bestseller_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books;







}
