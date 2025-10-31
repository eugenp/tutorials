package com.baeldung.spring.aotrepository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.spring.aotrepository.entity.User;
import com.baeldung.spring.aotrepository.repository.UserRepository;

import jakarta.transaction.Transactional;

@SpringBootTest(classes = Application.class)
@Transactional
class ExtendingRepositoryTest {

    @Autowired private UserRepository userRepository;

    @Test
    void givenUserRepository_whenFindById_thenCorrect() {
        User user = new User("firstname", "lastname");

        User saved = userRepository.save(user);
        System.out.println(saved);
        List<User> allUsers = userRepository.findAll();

        assertTrue(allUsers.contains(user));
    }
}
