package com.baeldung.autowired;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class TypesOfBeanInjectionSpring {
    private final UserService userService;

    @Autowired // the @Autowired can even be omitted, in case there's only one explicit constructor
    public TypesOfBeanInjectionSpring(UserService userService) {
        this.userService = userService;
    }
    
    public static void main(String[] args) {
        SpringApplication.run(TypesOfBeanInjectionSpring.class, args);
    }

    @Bean
    CommandLineRunner runIt() {
        return args -> {
            userService.listUsers()
                .stream()
                .forEach(System.out::println);
        };
    }
}

class User {
    private String name;

    public User(String name) {
        this.name = name;
    }

    // getters and setters ...
    public String getName() {
        return this.name;
    }

    public String toString() {
        return name;
    }

}

interface UserService {
    List<User> listUsers();
}

@Service
class UserServiceImpl implements UserService {

    @Override
    public List<User> listUsers() {
        ArrayList<User> users = new ArrayList<>(3);
        users.add(new User("Snoopy"));
        users.add(new User("Woodstock"));
        users.add(new User("Charlie Brown"));
        return users;
    }

}
