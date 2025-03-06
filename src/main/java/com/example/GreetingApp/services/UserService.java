package com.example.GreetingApp.services;

import com.example.GreetingApp.model.User;
import com.example.GreetingApp.repository.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    EmailService emailService;
    @Autowired
    AuthUser userRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public User registerUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean matchPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
    public ResponseEntity<Map<String, String>> forgotPassword(String email, String newPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        Map<String, String> response = new HashMap<>();

        if (userOptional.isEmpty()) {
            response.put("message", "Sorry! We cannot find the user email: " + email);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        User user = userOptional.get();
        user.setPassword(passwordEncoder.encode(newPassword)); // Hash the new password
        userRepository.save(user);

        emailService.sendEmail(email, "Password Reset", "Your password has been changed successfully!");

        response.put("message", "Password has been changed successfully!");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Map<String, String>> resetPassword(String email, String currentPassword, String newPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        Map<String, String> response = new HashMap<>();

        if (userOptional.isEmpty()) {
            response.put("message", "User not found with email: " + email);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            response.put("message", "Current password is incorrect!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        user.setPassword(passwordEncoder.encode(newPassword)); // Hash the new password
        userRepository.save(user);

        response.put("message", "Password reset successfully!");
        return ResponseEntity.ok(response);
    }
}