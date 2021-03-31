package com.baeldung.ignorable.fields;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.*;
import java.util.List;
import java.util.Random;

import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

public class TransientFieldUnitTest {

    private final UserDao userDao = new UserDao();

    private final int randInt = new Random().nextInt();

    private final User user = new User("user" + randInt + "@bar.com", "hunter2", "MacOSX");

    @Ignore
    @Test
    public void givenFieldWithTransientAnnotation_whenSavingViaJPA_thenFieldIgnored() {
        userDao.saveUser(user);
        List<User> allUsers = userDao.getUsers();
        User savedUser = allUsers.get(allUsers.indexOf(user));

        assertNull(savedUser.getCurrentDevice());
    }

    @Test
    public void givenFieldWithTransientAnnotation_whenSerializingObject_thenFieldSerialized() throws IOException, ClassNotFoundException {

        FileOutputStream fout = new FileOutputStream("test.obj");
        ObjectOutputStream out = new ObjectOutputStream(fout);
        out.writeObject(user);
        out.flush();
        out.close();

        FileInputStream fin = new FileInputStream("test.obj");
        ObjectInputStream in = new ObjectInputStream(fin);
        User savedUser = (User) in.readObject();
        in.close();

        assertEquals(user.getCurrentDevice(), savedUser.getCurrentDevice());
    }

    @Test
    public void givenFieldWithTransientAnnotation_whenSerializingToJSON_thenFieldSerialized() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);
        User savedUser = objectMapper.readValue(json, User.class);

        assertEquals(user.getCurrentDevice(), savedUser.getCurrentDevice());
    }

    @Test
    public void givenJacksonHibernate5Module_whenSerializingTransientAnnotation_thenFieldIgnored() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Hibernate5Module());
        String json = objectMapper.writeValueAsString(user);
        User savedUser = objectMapper.readValue(json, User.class);

        assertNull(savedUser.getCurrentDevice());
    }

    @Test
    public void givenPropagateTransientFieldFlag_whenSerializingTransientAnnotation_thenFieldSerialized() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
        String json = objectMapper.writeValueAsString(user);
        User savedUser = objectMapper.readValue(json, User.class);

        assertEquals(user.getCurrentDevice(), savedUser.getCurrentDevice());
    }
}
