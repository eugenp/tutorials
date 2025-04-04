package com.baeldung.objenesis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisHelper;
import org.objenesis.ObjenesisSerializer;
import org.objenesis.ObjenesisStd;

public class ObjenesisTest {

    @Test
    void givenObjenesisStd_whenCreatingUser_thenObjectIsCreatedWithoutConstructor() {
        Objenesis objenesis = new ObjenesisStd();
        User user = objenesis.newInstance(User.class);
        assertNotNull(user);

        user.setName("Harry Potter");
        assertEquals("Harry Potter", user.getName());
    }

    @Test
    void givenObjenesisSerializer_whenCreatingUser_thenObjectIsCreatedWithoutConstructor() {
        Objenesis objenesis = new ObjenesisSerializer();
        User user = objenesis.newInstance(User.class);
        assertNotNull(user);

        user.setName("Harry Potter");
        assertEquals("Harry Potter", user.getName());
    }

    @Test
    void givenObjenesisHelper_whenCreatingUser_thenObjectIsCreatedWithoutConstructor() {
        User user = ObjenesisHelper.newInstance(User.class);
        assertNotNull(user);

        user.setName("Harry Potter");
        assertEquals("Harry Potter", user.getName());
    }

    @Test
    void givenObjenesisHelper_whenCreatingSerializableUser_thenObjectIsCreatedWithoutConstructor() {
        User user = ObjenesisHelper.newSerializableInstance(User.class);
        assertNotNull(user);

        user.setName("Harry Potter");
        assertEquals("Harry Potter", user.getName());
    }

}
