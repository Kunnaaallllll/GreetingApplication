package com.example.GreetingApp.repository;


import com.example.GreetingApp.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthUser extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}