package com.baeldung.jpadefaultvalues.application;

import com.baeldung.jpadefaultvalues.application.User;
import com.baeldung.jpadefaultvalues.application.UserRepository;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDefaultValuesUnitTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Ignore // SQL default values are also defined
    public void saveUser_shouldSaveWithDefaultFieldValues() {
        User user = new User();
        user = userRepository.save(user);

        assertEquals(user.getName(), "John Snow");
        assertEquals(25, (int) user.getAge());
        assertFalse(user.getLocked());
    }

    @Test
    @Ignore // SQL default values are also defined
    public void saveUser_shouldSaveWithNullName() {
        User user = new User();
        user.setName(null);
        user = userRepository.save(user);

        assertNull(user.getName());
        assertEquals(25, (int) user.getAge());
        assertFalse(user.getLocked());
    }

    @Test
    public void saveUser_shouldSaveWithDefaultSqlValues() {
        User user = new User();
        user = userRepository.save(user);

        assertEquals(user.getName(), "John Snow");
        assertEquals(25, (int) user.getAge());
        assertFalse(user.getLocked());
    }

}
