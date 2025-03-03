package com.example.GreetingApp.controller;
import com.example.GreetingApp.services.GreetingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/greet")
public class GreetingController {

    private final GreetingService greetingService;

    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

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

    @GetMapping("/hello")
    public Greeting getSimpleGreeting() {
        return new Greeting(greetingService.getSimpleGreeting());
    }

    @GetMapping("/greetUser")
    public Greeting getPersonalizedGreeting(@RequestParam(required = false) String firstName,
                                            @RequestParam(required = false) String lastName) {
        return new Greeting(greetingService.getGreetingMessage(firstName, lastName));
    }
    @PostMapping("/save")
    public Greeting saveGreeting(@RequestParam String message) {
        return greetingService.saveGreeting(message);
    }
    @GetMapping("/getById")
    public Greeting getGreetingById(@RequestParam Long id) {
        return greetingService.getGreetingById(id);
    }
    @GetMapping("/all")
    public List<Greeting> getAllGreetings() {
        return greetingService.getAllGreetings();
    }
    @PutMapping("/edit")
    public Greeting editGreeting(@RequestParam Long id, @RequestParam String message) {
        return greetingService.editGreeting(id, message);
    }
    @DeleteMapping("/delete")
    public String deleteGreeting(@RequestParam Long id) {
        return greetingService.deleteGreeting(id);
    }

}
