package com.example.GreetingApp.repository;
import com.example.GreetingApp.controller.Greeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GreetingRepository extends JpaRepository<Greeting, Long> {}