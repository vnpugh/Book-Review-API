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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Integer getWeeks() {
        return weeks;
    }

    public void setWeeks(Integer weeks) {
        this.weeks = weeks;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "BestSeller{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", rating=" + rating +
                ", weeks=" + weeks +
                ", sales=" + sales +
                ", books=" + books +
                '}';
    }
}
