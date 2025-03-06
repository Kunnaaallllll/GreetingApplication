package com.example.GreetingApp.controller;



import com.example.GreetingApp.dto.LoginDTO;
import com.example.GreetingApp.dto.RegisterDTO;
import com.example.GreetingApp.model.User;
import com.example.GreetingApp.services.EmailService;
import com.example.GreetingApp.services.UserService;
import com.example.GreetingApp.utility.JwtUtility;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    JwtUtility jwtUtility;
    @Autowired
    EmailService emailService;


    //Ye Ek REST API banayenge jo user ko register karegi.
    @PostMapping("/register")
    public String registeruser(@Valid @RequestBody RegisterDTO registerUser){
        if(userService.existsByEmail(registerUser.getEmail())){
            return "User Already Exists";
        }

        User user = new User();
        user.setFullName(registerUser.getFullName());
        user.setEmail(registerUser.getEmail());
        user.setPassword(registerUser.getPassword());

        User savedUser = userService.registerUser(user);
        String token = jwtUtility.generateToken(savedUser.getEmail());
        String subject = "Welcome to Our Platform!";
        String body = "Hello " + savedUser.getFullName() + ",\n\nYour account has been successfully created!";
        emailService.sendEmail(savedUser.getEmail(), subject, body);
        return "User Registered Successfully and Your Token is: "+token;
    }

    @PostMapping("/login")
    public String loginUser(@Valid@RequestBody LoginDTO loginUser){
        Optional<User> userExists = userService.getUserByEmail(loginUser.getEmail());
        if(userExists.isPresent()){
            User user = userExists.get();
            if(userService.matchPassword(loginUser.getPassword(),user.getPassword())){
                String token = jwtUtility.generateToken(user.getEmail());
                String subject = "Welcome Back to Our Platform!";
                String body = "Hello " + user.getFullName() + ",\n\nYour account has been successfully Logged In! and Your Token is: "+token;
                emailService.sendEmail(user.getEmail(), subject, body);
                return "User Login Successfully and Token is: "+token;
            }
            else{
                return "InValid Crendentials";
            }
        }
        else{
            return "User Not Found";
        }
    }
    @PutMapping("/forgotPassword/{email}")
    public ResponseEntity<Map<String, String>> forgotPassword(@PathVariable String email, @RequestBody Map<String, String> request) {
        String newPassword = request.get("password");
        return userService.forgotPassword(email, newPassword);
    }


    @PutMapping("/resetPassword/{email}")
    public ResponseEntity<Map<String, String>> resetPassword(
            @PathVariable String email,
            @RequestParam String currentPassword,
            @RequestParam String newPassword) {
        return userService.resetPassword(email, currentPassword, newPassword);
    }
}