package com.baeldung;

import com.baeldung.h2db.springboot.SpringBootH2Application;
import com.baeldung.h2db.springboot.daos.UserRepository;
import com.baeldung.h2db.springboot.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootH2Application.class)
public class SpringBootH2IntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void contextLoads() { }

    @Test
    public void givenUserProfile_whenAddUser_thenCreateNewUser() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        userRepository.save(user);
        List<User> users = (List<User>) userRepository.findAll();
        assertFalse(users.isEmpty());

        String firstName = "Aliko";
        String lastName = "Dangote";
        User user1 = userRepository.findById(users.get(0).getId()).get();
        user1.setLastName(lastName);
        user1.setFirstName(firstName);
        userRepository.save(user1);

        user = userRepository.findById(user.getId()).get();
        assertEquals(user.getFirstName(), firstName);
        assertEquals(user.getLastName(), lastName);

        userRepository.deleteById(user.getId());
        assertTrue( ((List<User>) userRepository.findAll()).isEmpty());
    }

}
