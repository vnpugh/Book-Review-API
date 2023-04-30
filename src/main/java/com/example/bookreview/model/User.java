package com.example.bookreview.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.mapping.List;

import javax.persistence.*;

@Entity
@Table(name = "users")//always make sure it is with a "s" w/Postgres
public class User {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String userName;
    @Column(unique = true)
    private String emailAddress;

    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @OneToOne(cascade = CascadeType.ALL)//pull the user and the profile as well
    @JoinColumn(name = "profile_id", referencedColumnName = "id") //adding a FK
    private UserProfile userProfile;


    @ManyToMany
    @JoinTable(
            name = "reviews",
            joinColumns = @JoinColumn(name = "userName"),
            inverseJoinColumns = @JoinColumn(name = "review_id"))

    private List<Review> reviewList;

    public User() {
    }

    public User(Long id, String userName, String emailAddress, String password) {
        this.id = id;
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.password = password;

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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", password='" + password + '\'' +
                ", userProfile=" + userProfile +
                ", reviewList=" + reviewList +
                '}';
    }

    public boolean isLoggedIn() {
        return false;
    }
}