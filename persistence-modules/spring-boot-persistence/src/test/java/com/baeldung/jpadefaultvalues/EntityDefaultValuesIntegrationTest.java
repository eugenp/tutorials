package com.baeldung.jpadefaultvalues;

import com.baeldung.jpadefaultvalues.User;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = ContactApp.class)
public class EntityDefaultValuesIntegrationTest {

    @Test
    void saveUser_shouldSaveWithDefaultFieldValues() {
        User user = new User();
        user = userRepository.save(user);

        assertEquals(user.getName(), "John Snow");
        assertEquals(25, (int) user.getAge());
        assertFalse(user.getLocked());
    }

    @Test
    void saveUser_shouldSaveWithNullName() {
        User user = new User();
        user.setName(null);
        user = userRepository.save(user);

        assertNull(user.getName());
        assertEquals(25, (int) user.getAge());
        assertFalse(user.getLocked());
    }

    @Test
    void saveUser_shouldSaveWithDefaultSqlValues() {
        User user = new User();
        user = userRepository.save(user);

        assertEquals(user.getName(), "John Snow");
        assertEquals(25, (int) user.getAge());
        assertFalse(user.getLocked());
    }

}
