package com.example.bookreview.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private User user;



    public Review(Long id, String userName, String author, String title, LocalDate reviewDate, String reviewText, Book book) {
        this.id = id;
        this.userName = userName;
        this.author = author;
        this.title = title;
        this.reviewDate = reviewDate;
        this.reviewText = reviewText;
        this.book = book;
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

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", author=" + author +
                ", title='" + title + '\'' +
                ", reviewDate=" + reviewDate +
                ", reviewText='" + reviewText + '\'' +
                '}';
    }
}
