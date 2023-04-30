package com.example.bookreview.security;
/**
import com.example.bookreview.model.User;
import com.example.bookreview.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }


    @Override //need the user details after we find the user
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = userService.findUserByEmailAddress(email);
        return new MyUserDetails(user);
    }




}
*/