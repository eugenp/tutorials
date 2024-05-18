package com.baeldung.serialization.protocols;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonSerializationUnitTest {

    @Test
    public void whenSerializedUsingGson_ThenObjectIsSameAfterDeserialization() {
       
        User user = new User();
        user.setId(1);
        user.setName("Mark");

        String filePath = "src/test/resources/protocols/gson_user.json";

        try (Writer writer = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(user, writer);
            
            assertTrue(Files.exists(Paths.get(filePath)));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            User deserializedUser = gson.fromJson(new FileReader(filePath), User.class);
            
            assertEquals(1, deserializedUser.getId());
            assertEquals("Mark", deserializedUser.getName());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}