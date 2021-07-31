package com.baeldung.serialization.protocols;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonSerializationUnitTest {

    @Test
    public void whenUsingJacksonAPIForJSONSerialization_thenDeserializeCorrectObject() throws IOException {

        User user = new User();
        user.setId(1);
        user.setName("Mark Jonson");

        String filePath = "src/test/resources/protocols/jackson_user.json";

        File file = new File(filePath);
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, user);

        User deserializedUser = mapper.readValue(new File(filePath), User.class);
        
        assertEquals(1, deserializedUser.getId());
        assertEquals("Mark Jonson", deserializedUser.getName());
    }

    public static List<User> populateListOfUsers() {
        User user1 = new User();
        user1.setId(1);
        user1.setName("Mark Jonson");

        User user2 = new User();
        user2.setId(2);
        user2.setName("Johny Beth");

        User user3 = new User();
        user3.setId(3);
        user3.setName("Eliza Green");

        User user4 = new User();
        user4.setId(4);
        user4.setName("Monica Doe");

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);

        return users;
    }
}