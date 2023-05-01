package com.example.bookreview.service;



import com.example.bookreview.exception.InformationExistException;
import com.example.bookreview.exception.UserNotFoundException;
import com.example.bookreview.exception.UserNotLoggedInException;
import com.example.bookreview.model.User;
import com.example.bookreview.model.UserProfile;
import com.example.bookreview.model.request.LoginRequest;
import com.example.bookreview.model.response.LoginResponse;
import com.example.bookreview.repository.UserRepository;
import com.example.bookreview.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private JWTUtils jwtUtils;
    private AuthenticationManager authenticationManager;
    private MyUserDetails myUserDetails;
    private UserProfile userProfile;

    /**
     * Constructor for the UserService class
     * dependency injection
     * lazy loading which means that the bean will be created only when it is needed.
     * passwordEncoder is used to encode the password.
     * jwtUtils is used to generate the JWT token.
     * authenticationManager is used to authenticate the user.
     * myUserDetails is used to load the user details.
     */
    @Autowired
    public UserService(UserRepository userRepository,
                       @Lazy PasswordEncoder passwordEncoder, JWTUtils jwtUtils,
                       @Lazy AuthenticationManager authenticationManager,
                       @Lazy MyUserDetails myUserDetails) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.myUserDetails = myUserDetails;
    }

    /**
     * This method is used to create a new user.
     * It checks if the user already exists in the database.
     * If the user does not exist, it encodes the password and saves the user in the database.
     * If the user already exists, it throws an InformationExistException.
     */
    public User createUser(User userObject) {
        if (!userRepository.existsByEmailAddress(userObject.getEmailAddress())) {
            userObject.setPassword(passwordEncoder.encode(userObject.getPassword()));
            return userRepository.save(userObject);
        } else {
            throw new InformationExistException("user with email address " + userObject.getEmailAddress() +
                    " already exists");
        }
    }

    public User findUserByEmailAddress(String email) { return userRepository.findUserByEmailAddress(email);}

    /**
     * This method is used to login the user - the logic.
     * It authenticates the user and generates the JWT token.
     * If the user does not exist, it throws an AuthenticationException.
     * If the password is incorrect, it throws an AuthenticationException.
     * If the user is not logged in, it throws a UserNotLoggedInException.
     * If the user is logged in, it returns the JWT token.
     * @param loginRequest
     */
    public ResponseEntity<?> loginUser(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            myUserDetails = (MyUserDetails) ((org.springframework.security.core.Authentication) authentication).getPrincipal();

            final String JWT = jwtUtils.generateJwtToken(myUserDetails);
            return ResponseEntity.ok(new LoginResponse(JWT));
        } catch (Exception e) {
            return ResponseEntity.ok(new LoginResponse("Error : username or password is incorrect"));
        }
    }


    /**
     * This method is used to update the user.
     * It checks if the user exists in the database.
     * If the user does not exist, it throws a UserNotFoundException.
     * If the user is not logged in, it throws a UserNotLoggedInException.
     * If the user exists and is logged in, it updates the user and returns the updated user.
     * @param userId
     * @param userObject
     */
    public User updateUser(Long userId, User userObject) throws UserNotFoundException, UserNotLoggedInException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id : " + userId));

        // check if the user is logged in
        if (!isLoggedIn(user)) {
            throw new UserNotLoggedInException("User not logged in.");
        }

        // Validate the input user object
        String username = userObject.getUserName();
        String email = userObject.getEmailAddress();
        String password = userObject.getPassword();

        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }

        if (email == null || email.isEmpty() || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email address.");
        }

        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        }

        // Update the user
        user.setUserName(username);
        user.setEmailAddress(email);
        user.setPassword(password);

        return userRepository.save(user);
    }

    /**
     * checks if a given user is logged-in currently by comparing the username of the User
       with the username of the currently authenticated user from the SecurityContextHolder.
     * @param user
     * @return false if the user is not logged in, true otherwise.
     */
    private boolean isLoggedIn(User user) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                return userDetails.getUsername().equals(user.getUserName());
            }
        }
        return false;
    }
}








