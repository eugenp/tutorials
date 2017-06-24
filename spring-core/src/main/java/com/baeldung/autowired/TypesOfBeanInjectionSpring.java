package com.baeldung.autowired;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class TypesOfBeanInjectionSpring {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(TypesOfBeanInjectionSpring.class, args);

        // Get bean from the Spring Application Context:
        UserControllerConstructorInjection userControllerConstructorInjection = context.getBean(UserControllerConstructorInjection.class);

        userControllerConstructorInjection.listUsers()
            .stream()
            .forEach(System.out::println);

        // Get bean from the Spring Application Context:
        UserControllerFieldInjection userControllerFieldInjection = context.getBean(UserControllerFieldInjection.class);

        userControllerFieldInjection.listUsers()
            .stream()
            .forEach(System.out::println);

        // Get bean from the Spring Application Context:
        UserControllerSetterInjection controllerSetterInjection = context.getBean(UserControllerSetterInjection.class);

        controllerSetterInjection.listUsers()
            .stream()
            .forEach(System.out::println);
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

@Controller
class UserControllerConstructorInjection {
    private final UserService userService; // the keyword 'final' assuring immutability of the object.

    @Autowired // the @Autowired can even be omitted, in case there's only one explicit constructor
    public UserControllerConstructorInjection(UserService userService) {
        this.userService = userService;
    }

    public List<User> listUsers() {
        return this.userService.listUsers();
    }
}

@Controller
class UserControllerFieldInjection {
    @Autowired
    private UserService userService;

    public List<User> listUsers() {
        return this.userService.listUsers();
    }
}

@Controller
class UserControllerSetterInjection {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public List<User> listUsers() {
        return this.userService.listUsers();
    }
}