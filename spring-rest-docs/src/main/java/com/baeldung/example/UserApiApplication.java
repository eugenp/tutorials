package com.baeldung.example;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.config.EnableHypermediaSupport;

import com.baeldung.example.model.User;

@SpringBootApplication
@EnableHypermediaSupport(type = HAL)
public class UserApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApiApplication.class, args);
    }

    @Bean
    public Map<String, User> configuredUsers() {
        Map<String, User> configuredUsers = new HashMap<>();
        User user1 = new User("example.com", "com", "2016-09-12", Boolean.TRUE);
        User user2 = new User("example.nyc", "nyc", "2016-010-22", Boolean.FALSE);
        User user3 = new User("example.london", "london", "2016-11-30", Boolean.FALSE);
        User user4 = new User("example.paris", "paris", "2016-02-06", Boolean.TRUE);
        User user5 = new User("example.camera", "camera", "2016-05-17", Boolean.TRUE);
        User user6 = new User("example.photography", "photography", "2016-02-20", Boolean.FALSE);


        configuredUsers.put(user1.getName(), user1);
        configuredUsers.put(user2.getName(), user2);
        configuredUsers.put(user3.getName(), user3);
        configuredUsers.put(user4.getName(), user4);
        configuredUsers.put(user5.getName(), user5);
        configuredUsers.put(user6.getName(), user6);

        return configuredUsers;
    }
}
