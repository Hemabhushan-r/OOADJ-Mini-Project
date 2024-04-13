package com.stockportfolio.stockservice.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockportfolio.stockservice.Classes.Request.UserSignUpRequest;
import com.stockportfolio.stockservice.Classes.Response.ApiResponse;
import com.stockportfolio.stockservice.Classes.Response.JwtAuthenticationResponse;
import com.stockportfolio.stockservice.Exceptions.UserPasswordMismatchException;
import com.stockportfolio.stockservice.Models.User;
import com.stockportfolio.stockservice.Security.JwtService;
import com.stockportfolio.stockservice.Services.UserService;

import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserManagementController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody User user) {
        try {
            User existingUser = userService.authenticateByEmail(user.getEmail(), user.getPassword());
        } catch (UserPasswordMismatchException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String token = jwtService.generateToken(user.getEmail());
        // Return the token in the response
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signUp(@RequestBody UserSignUpRequest userSignUpRequest) {

        try {
            // Check if username or email already exists
            if (userService.existsByUsername(userSignUpRequest.getUsername())) {
                ApiResponse response = new ApiResponse(false, "Username is already taken!");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            if (userService.existsByEmail(userSignUpRequest.getEmail())) {
                ApiResponse response = new ApiResponse(false, "Email Address is already in use!");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            // System.out.println("here1");

            // Create a new User object
            User user = new User();
            user.setUsername(userSignUpRequest.getUsername());
            user.setEmail(userSignUpRequest.getEmail());
            user.setPassword(passwordEncoder.encode(userSignUpRequest.getPassword()));
            user.setPanNumber(userSignUpRequest.getPanNumber());
            user.setPhoneNumber(userSignUpRequest.getPhoneNumber());
            user.setVerified(false);
            user.setCreatedAt(new Date());
            user.setRoles("ROLE_USER"); // Set default role

            // Save the user to the database
            userService.createUser(user);
            // Generate JWT token
            String token = jwtService.generateToken(user.getEmail());
            // Return the token in the response
            return ResponseEntity.ok(new JwtAuthenticationResponse(token));
            // return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true,
            // token));
        } catch (Exception e) {
            System.out.println(e);
            ApiResponse response = new ApiResponse(false, "An error occurred while processing your request.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}