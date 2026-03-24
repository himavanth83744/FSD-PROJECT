package com.veltech.portal.controller;

import com.veltech.portal.model.User;
import com.veltech.portal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

/**
 * REST Controller for Authentication.
 * Base URL: /api/auth
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // POST /api/auth/signup
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, String> body) {
        String email      = body.get("email");
        String rollNumber = body.get("rollNumber");
        String password   = body.get("password");
        String fullName   = body.get("fullName");

        if (userRepository.existsByEmail(email))
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Email already registered."));
        if (userRepository.existsByRollNumber(rollNumber))
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Roll number already registered."));

        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setRollNumber(rollNumber);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "Account created successfully."));
    }

    // POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username"); // email or roll number
        String password = body.get("password");

        Optional<User> userOpt = userRepository.findByEmail(username)
                .or(() -> userRepository.findByRollNumber(username));

        if (userOpt.isEmpty())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "User not found."));

        User user = userOpt.get();
        if (!passwordEncoder.matches(password, user.getPassword()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid password."));

        return ResponseEntity.ok(Map.of(
            "message", "Login successful.",
            "name", user.getFullName(),
            "roll", user.getRollNumber(),
            "role", user.getRole()
        ));
    }
}
