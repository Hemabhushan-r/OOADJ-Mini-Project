package com.stockportfolio.stockservice.Controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class UserManagementController {

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}