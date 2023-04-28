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
     *a book can also be associated with many bestsellers -> bidirectional relationship.
     *non-owning entity = book b/c the join table and foreign key columns are not defined -> uses mapped by instead.
     *owning-entity = bestseller -> manages the join table and the foreign key columns.
     *this is why the inverseJoinColumns is needed.
     */

    @ManyToMany
    @JoinTable(
            name = "bestsellers_books",
            joinColumns = @JoinColumn(name = "bestseller_id"), //foreign key
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books;


    public BestSeller(Long id, String title, String author, String genre, double rating,
                      Integer weeks, Integer sales, List<Book> books) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.rating = rating;
        this.weeks = weeks;
        this.sales = sales;
        this.books = books;
    }

    public BestSeller() {
    }

    


}
