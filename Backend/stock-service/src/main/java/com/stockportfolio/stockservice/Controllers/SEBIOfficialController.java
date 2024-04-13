package com.stockportfolio.stockservice.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockportfolio.stockservice.Security.JwtService;
import com.stockportfolio.stockservice.Services.UserService;
import com.stockportfolio.stockservice.Classes.Request.VerifyPanRequest;
import com.stockportfolio.stockservice.Classes.Response.ApiResponse;
import com.stockportfolio.stockservice.Models.PendingUser;

import lombok.AllArgsConstructor;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/sebi")
public class SEBIOfficialController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/pending-users")
    public ResponseEntity<List<PendingUser>> pendingUsers() {
        return ResponseEntity.ok(userService.getAllPendingVerificationUsers());
    }

    @PostMapping("/verify-pan")
    public ResponseEntity<?> verifyPan(@RequestBody VerifyPanRequest request) {
        return ResponseEntity.ok(new ApiResponse());
    }
}
