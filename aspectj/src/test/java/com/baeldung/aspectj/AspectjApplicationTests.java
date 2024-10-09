package com.baeldung.aspectj;

import com.baeldung.aspectj.service.MessageService;
import com.baeldung.aspectj.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AspectjApplicationTests {
    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @Test
    void contextLoads() {
    }

    @Test
    void testUserService() {
        userService.createUser("create new user john", 21);
        userService.createUser("create new user doe", 32);
        userService.deleteUser("delete user john");
        userService.deleteUser("delete user doe");
        messageService.sendMessage("send message from user john");
        messageService.receiveMessage("receive message from user john");
    }

}
