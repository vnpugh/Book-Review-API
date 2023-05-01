package com.example.bookreview.security;


import com.example.bookreview.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.HashSet;

public class MyUserDetails implements UserDetails {

    /**
     * UserDetails is an interface that Spring Security uses for user authentication.
     * this method is important because it allows you to access the user's details and custom
       properties.
     * This class will be used to create a UserDetails object that will be used by Spring Security.
     * override the methods in the UserDetails interface.
     */

    private final User user;

    public MyUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<>();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmailAddress();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    //IMPORTANT METHOD!!
    public User getUser() {
        return user; //grabs the credentials & returns the user object
    }

}

