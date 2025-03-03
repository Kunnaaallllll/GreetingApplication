package com.example.GreetingApp;
import org.springframework.web.bind.annotation.*;

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
}
