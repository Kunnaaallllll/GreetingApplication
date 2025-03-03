package com.example.GreetingApp;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GreetingService {
    private final GreetingRepository greetingRepository;

    public GreetingService(GreetingRepository greetingRepository) {
        this.greetingRepository = greetingRepository;
    }

    public String getSimpleGreeting() {
        return "Hello World";
    }

    public String getGreetingMessage(String firstName, String lastName) {
        if (firstName != null && lastName != null) {
            return "Hello, " + firstName + " " + lastName + "!";
        } else if (firstName != null) {
            return "Hello, " + firstName + "!";
        } else if (lastName != null) {
            return "Hello, " + lastName + "!";
        } else {
            return getSimpleGreeting();
        }
    }

    public Greeting saveGreeting(String message) {
        return greetingRepository.save(new Greeting(message));
    }

    public Greeting getGreetingById(Long id) {
        return greetingRepository.findById(id).orElse(null);
    }
    public List<Greeting> getAllGreetings() {
        return greetingRepository.findAll();
    }
    public Greeting editGreeting(Long id, String message) {
        Greeting greeting=greetingRepository.findById(id).orElse(null);
        if(greeting!=null){
            greeting.setMessage(message);
            return greetingRepository.save(greeting);
        }
        return null;
    }
    public String deleteGreeting(Long id) {
        if(greetingRepository.existsById(id)) {
            greetingRepository.deleteById(id);
            return "Greeting with ID " + id + " deleted successfully.";
        }
        return "Greeting with ID " + id + " not found.";
    }
}
