package com.jobApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.jobApplication.security.JwtTokenProvider;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     * Generate JWT token
     * In a real application, you would validate credentials against a user database
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username) {
        // In production, verify username and password here
        String token = jwtTokenProvider.generateToken(username);
        
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("username", username);
        response.put("message", "Login successful");

        return ResponseEntity.ok(response);
    }

    /**
     * Validate JWT token
     */
    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String bearerToken) {
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid token format");
        }

        String token = bearerToken.substring(7);
        boolean isValid = jwtTokenProvider.validateToken(token);

        Map<String, Object> response = new HashMap<>();
        response.put("valid", isValid);
        if (isValid) {
            response.put("username", jwtTokenProvider.getUsernameFromToken(token));
        }

        return ResponseEntity.ok(response);
    }
}
