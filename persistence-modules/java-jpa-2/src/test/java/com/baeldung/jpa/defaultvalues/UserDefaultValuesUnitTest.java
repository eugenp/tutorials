package com.baeldung.jpa.defaultvalues;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import static org.junit.Assert.*;

public class UserDefaultValuesUnitTest {

    private static UserRepository userRepository = null;

    @BeforeClass
    public static void once() {
        userRepository = new UserRepository();
    }

    @Test
    @Ignore // SQL default values are also defined
    public void saveUser_shouldSaveWithDefaultFieldValues() {
        User user = new User();
        userRepository.save(user, 1L);

        user = userRepository.find(1L);
        assertEquals(user.getName(), "John Snow");
        assertEquals(25, (int) user.getAge());
        assertFalse(user.getLocked());
    }

    @Test
    @Ignore // SQL default values are also defined
    public void saveUser_shouldSaveWithNullName() {
        User user = new User();
        user.setName(null);
        userRepository.save(user, 2L);

        user = userRepository.find(2L);
        assertNull(user.getName());
        assertEquals(25, (int) user.getAge());
        assertFalse(user.getLocked());
    }

    @Test
    public void saveUser_shouldSaveWithDefaultSqlValues() {
        User user = new User();
        userRepository.save(user, 3L);

        user = userRepository.find(3L);
        assertEquals(user.getName(), "John Snow");
        assertEquals(25, (int) user.getAge());
        assertFalse(user.getLocked());
    }

    @AfterClass
    public static void destroy() {
        userRepository.clean();
    }

}
