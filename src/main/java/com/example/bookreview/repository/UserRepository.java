package com.example.bookreview.repository;

import com.example.bookreview.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    //user can register custom method
    boolean existsByEmailAddress(String emailAddress);

    //user can log in custom method
    User findUserByEmailAddress(String emailAddress);


}
