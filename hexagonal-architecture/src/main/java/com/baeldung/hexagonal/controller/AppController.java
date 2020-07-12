package main.java.com.baeldung.hexagonal.controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
