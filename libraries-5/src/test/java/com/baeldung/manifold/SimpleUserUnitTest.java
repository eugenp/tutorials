package com.baeldung.manifold;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleUserUnitTest {
    @Test
    void whenConstructingSimpleUser_thenValuesCanBeRetrieved() {
        SimpleUser user = SimpleUser.create("testuser", "Test User");
        user.setEmail("testuser@example.com");

        assertEquals("testuser", user.getUsername());
        assertEquals("Test User", user.getName());
        assertEquals("testuser@example.com", user.getEmail());
    }

    @Test
    void whenBuildingSimpleUser_thenValuesCanBeRetrieved() {
        SimpleUser user = SimpleUser.builder("testuser", "Test User")
            .withEmail("testuser@example.com")
            .build();

        assertEquals("testuser", user.getUsername());
        assertEquals("Test User", user.getName());
        assertEquals("testuser@example.com", user.getEmail());
    }

    @Test
    void whenParsingSimpleUserFromString_thenValuesCanBeRetrieved() {
        SimpleUser user = SimpleUser.load().fromJson("""
            {
                "username": "testuser",
                "name": "Test User",
                "email": "testuser@example.com"
            }
            """);

        assertEquals("testuser", user.getUsername());
        assertEquals("Test User", user.getName());
        assertEquals("testuser@example.com", user.getEmail());
    }

    @Test
    void whenParsingSimpleUserFromFile_thenValuesCanBeRetrieved() {
        InputStream is = getClass().getResourceAsStream("/com/baeldung/manifold/simpleUserData.json");
        InputStreamReader reader = new InputStreamReader(is);
        SimpleUser user = SimpleUser.load().fromJsonReader(reader);

        assertEquals("testuser", user.getUsername());
        assertEquals("Test User", user.getName());
        assertEquals("testuser@example.com", user.getEmail());
    }

    @Test
    void whenRenderingSimpleUserToString_thenCorrectJsonIsProduced() {
        SimpleUser user = SimpleUser.builder("testuser", "Test User")
            .withEmail("testuser@example.com")
            .build();
        assertEquals("""
                {
                  "username": "testuser",
                  "name": "Test User",
                  "email": "testuser@example.com"
                }""", user.write().toJson());
    }

    @Test
    void whenRenderingSimpleUserToWriter_thenCorrectJsonIsProduced() {
        SimpleUser user = SimpleUser.builder("testuser", "Test User")
            .withEmail("testuser@example.com")
            .build();

        StringWriter stringWriter = new StringWriter();
        user.write().toJson(stringWriter);

        assertEquals("""
                {
                  "username": "testuser",
                  "name": "Test User",
                  "email": "testuser@example.com"
                }""", stringWriter.toString());
    }

    @Test
    void whenRenderingSimpleUserToString_thenCorrectYamlIsProduced() {
        SimpleUser user = SimpleUser.builder("testuser", "Test User")
            .withEmail("testuser@example.com")
            .build();
        assertEquals("""
            username: testuser
            name: Test User
            email: testuser@example.com
            """, user.write().toYaml());
    }

    @Test
    void whenRenderingSimpleUserToString_thenCorrectCsvIsProduced() {
        SimpleUser user = SimpleUser.builder("testuser", "Test User")
            .withEmail("testuser@example.com")
            .build();
        assertEquals("""
            "username","name","email"
            "testuser","Test User","testuser@example.com"
            """, user.write().toCsv());
    }

    @Test
    void whenRenderingSimpleUserToString_thenCorrectXmlIsProduced() {
        SimpleUser user = SimpleUser.builder("testuser", "Test User")
            .withEmail("testuser@example.com")
            .build();
        assertEquals("""
            <root_object username="testuser" name="Test User" email="testuser@example.com"/>
            """, user.write().toXml());
    }
}
