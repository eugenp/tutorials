package com.baeldung.aspectj;

import com.baeldung.aspectj.service.MessageService;
import com.baeldung.aspectj.service.UserService;
import com.baeldung.aspectj.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AspectjApplicationTests {
    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @Autowired
    UserRepository userRepository;

    @Test
    void testUserService() {
        userService.createUser("create new user john", 21);
        userService.deleteUser("john");
    }

    @Test
    void testMessageService() {
        messageService.sendMessage("send message from user john");
        messageService.receiveMessage("receive message from user john");
    }

    @Test
    void testUserRepository() {
        userRepository.createUser("john", 21);
        userRepository.deleteUser("john");
    }
}
