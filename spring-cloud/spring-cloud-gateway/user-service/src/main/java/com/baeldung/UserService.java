package com.baeldung;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableEurekaClient
@SpringBootApplication
public class UserService {
    public static void main(String[] args) {
        SpringApplication.run(UserService.class);
    }
}

@RestController
class UserController {

    List<String> users = Arrays.asList("JOHN", "PAUL", "RINGO", "GEORGE");

    @GetMapping(value = "/users/random")
    public String getUser() {

        Random rand = new Random();
        int randomNum = rand.nextInt(users.size());

        return "HELLO, " + users.get(randomNum);
    }
}