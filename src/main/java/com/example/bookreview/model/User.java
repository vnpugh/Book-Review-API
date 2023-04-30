package com.example.bookreview.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.mapping.List;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")//always make sure it is with a "s" w/Postgres
public class User {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long userId;
    @Column
    private String userName;
    @Column(unique = true)
    private String emailAddress;

    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @OneToOne(cascade = CascadeType.ALL)//pull the user and the profile as well //one to one relationship with user profile
    @JoinColumn(name = "profile_id", referencedColumnName = "id") //adding a FK
    private UserProfile userProfile;


    @ManyToMany
    @JoinTable(
            name = "reviews",
            joinColumns = @JoinColumn(name = "user"),
            inverseJoinColumns = @JoinColumn(name = "review_id"))
    private Set<Review> reviewList = new HashSet<>(); //Using a Set ensures that each review is unique for each user.



    public User() {
    }

    public User(Long id, Long userId, String userName, String emailAddress, String password, UserProfile userProfile, Set<Review> reviewList) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.userProfile = userProfile;
        this.reviewList = reviewList;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public Set<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(Set<Review> reviewList) {
        this.reviewList = reviewList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId=" + userId +
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