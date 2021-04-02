package com.baeldung.ignorable.fields;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TransientFieldUnitTest {

    private final UserDao userDao = new UserDao();

    private final int randInt = new Random().nextInt();

    private final User user = new User("user" + randInt + "@bar.com", "hunter2", "MacOSX");

    @Test
    public void givenFieldWithTransientAnnotation_whenSavingViaJPA_thenFieldIgnored() {
        userDao.saveUser(user);
        List<User> allUsers = userDao.getUsers();
        User savedUser = allUsers.get(allUsers.indexOf(user));

        assertNull(savedUser.getCurrentDevice());
    }

    @Test
    public void givenFieldWithTransientAnnotation_whenSerializingObject_thenFieldSerialized() throws IOException, ClassNotFoundException {

        try (FileOutputStream fout = new FileOutputStream("test.obj")) {
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(user);
            out.flush();
        }

        try (FileInputStream fin = new FileInputStream("test.obj")) {
            ObjectInputStream in = new ObjectInputStream(fin);
            User savedUser = (User) in.readObject();

            assertEquals(user.getCurrentDevice(), savedUser.getCurrentDevice());
        }

        Files.deleteIfExists(Paths.get("test.obj"));
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
