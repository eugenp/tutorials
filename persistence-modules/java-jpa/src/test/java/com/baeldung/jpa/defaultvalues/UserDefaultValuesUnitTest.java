package com.baeldung.jpa.defaultvalues;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import static org.junit.Assert.*;

public class UserDefaultValuesUnitTest {

    private static UserRepository userRepository = null;

    private static UserEntityRepository userEntityRepository = null;

    @BeforeClass
    public static void once() {
        userRepository = new UserRepository();
        userEntityRepository = new UserEntityRepository();
    }

    @Test
    @Ignore // SQL default values are also defined
    public void givenUser_whenUsingEntityValue_thenSavedWithDefaultValues() {
        User user = new User();
        userRepository.save(user, 1L);

        user = userRepository.find(1L);
        assertEquals(user.getName(), "John Snow");
        assertEquals(25, (int) user.getAge());
        assertFalse(user.getLocked());
    }

    @Test
    @Ignore // SQL default values are also defined
    public void givenUser_whenNameIsNull_thenSavedNameWithNullValue() {
        User user = new User();
        user.setName(null);
        userRepository.save(user, 2L);

        user = userRepository.find(2L);
        assertNull(user.getName());
        assertEquals(25, (int) user.getAge());
        assertFalse(user.getLocked());
    }

    @Test
    @Ignore
    public void givenUser_whenUsingSQLDefault_thenSavedWithDefaultValues() {
        User user = new User();
        userRepository.save(user, 3L);

        user = userRepository.find(3L);
        assertEquals(user.getName(), "John Snow");
        assertEquals(25, (int) user.getAge());
        assertFalse(user.getLocked());
    }

    @Test
    public void givenUser_whenSaveUsingQuery_thenSavedWithDefaultValues() {
        UserEntity user = new UserEntity();
        userEntityRepository.saveEntity(user, 2L);
        user = userEntityRepository.find(2L);
        assertEquals(user.getName(), "John Snow");
        assertEquals(25, (int) user.getAge());
        assertFalse(user.getLocked());
    }

    @Test
    public void givenUser_whenSaveUsingPrePersist_thenSavedWithDefaultValues() {
        UserEntity user = new UserEntity();
        userEntityRepository.save(user, 3L);
        user = userEntityRepository.find(3L);
        assertEquals(user.getName(), "John Snow");
        assertEquals(25, (int) user.getAge());
        assertFalse(user.getLocked());
    }

    @AfterClass
    public static void destroy() {
        userRepository.clean();
        userEntityRepository.clean();
    }

}
