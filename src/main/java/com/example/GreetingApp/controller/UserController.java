package com.example.GreetingApp.controller;



import com.example.GreetingApp.dto.RegisterDTO;
import com.example.GreetingApp.model.User;
import com.example.GreetingApp.services.EmailService;
import com.example.GreetingApp.services.UserService;
import com.example.GreetingApp.utility.JwtUtility;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}