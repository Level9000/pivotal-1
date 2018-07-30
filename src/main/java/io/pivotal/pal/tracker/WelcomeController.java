package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    private String result = "hello";


    public WelcomeController(@Value("${WELCOME_MESSAGE}") String message) {
        this.result = message;
    }

    @GetMapping("/")
    public String sayHello() {
        return result;
    }
}
