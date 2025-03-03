package com.example.GreetingApp;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/greet")
public class GreetingController {

    @GetMapping
    public Greeting getGreeting() {
        return new Greeting("Hello from GET");
    }

    @PostMapping
    public Greeting postGreeting() {
        return new Greeting("Hello from POST");
    }

    @PutMapping
    public Greeting putGreeting() {
        return new Greeting("Hello from PUT");
    }

    @DeleteMapping
    public Greeting deleteGreeting() {
        return new Greeting("Hello from DELETE");
    }
}
