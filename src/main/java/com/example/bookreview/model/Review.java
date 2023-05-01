package com.example.bookreview.model;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "reviews")
public class Review {


    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userName;
    @Column
    private String author;
    @Column
    private String title;
    @Column
    private LocalDate reviewDate;
    @Column
    private String reviewText;
    @Column
    private double rating;

    /**
     * The @ManyToOne annotation is used to specify the relationship between the Book and Review entities.
     * The @JoinColumn annotation is used to specify the foreign key column in the Review table.
     * The fetch type is set to LAZY, which means that the book object will be fetched when it is accessed.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    /**
     * The @ManyToOne annotation is used to specify the relationship between the User and Review entities.
     * The @JoinColumn annotation is used to specify the foreign key column in the Review table.
     * The fetch type is set to LAZY, which means that the user object will be fetched when it is accessed.
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private User user;


    public Review(Long id, String userName, String author, String title, LocalDate reviewDate,
                  String reviewText, Book book) {
        this.id = id;
        this.userName = userName;
        this.author = author;
        this.title = title;
        this.reviewDate = reviewDate;
        this.reviewText = reviewText;
        this.rating = rating;
        this.book = book;
        this.user = user;
    }

    public Review() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    //setter for review
    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", reviewDate=" + reviewDate +
                ", reviewText='" + reviewText + '\'' +
                ", rating=" + rating +
                ", book=" + book +
                ", user=" + user +
                '}';
    }
}
