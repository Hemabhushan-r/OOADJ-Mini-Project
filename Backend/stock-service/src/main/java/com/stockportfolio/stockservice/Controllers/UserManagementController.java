package com.stockportfolio.stockservice.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockportfolio.stockservice.Exceptions.UserPasswordMismatchException;
import com.stockportfolio.stockservice.Models.User;
import com.stockportfolio.stockservice.Services.UserService;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserManagementController {

    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @PostMapping("/signin")
    public ResponseEntity<User> signIn(@RequestBody User user) {
        try {
            User existingUser = userService.authenticateByEmail(user.getEmail(), user.getPassword());
        } catch (UserPasswordMismatchException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}