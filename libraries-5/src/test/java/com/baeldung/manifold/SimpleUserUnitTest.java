package com.baeldung.manifold;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleUserUnitTest {
    @Test
    void testCreateSimpleUser() {
        SimpleUser user = SimpleUser.create("testuser", "Test User");
        user.setEmail("testuser@example.com");

        assertEquals("testuser", user.getUsername());
        assertEquals("Test User", user.getName());
        assertEquals("testuser@example.com", user.getEmail());
    }

    @Test
    void testBuildSimpleUser() {
        SimpleUser user = SimpleUser.builder("testuser", "Test User")
            .withEmail("testuser@example.com")
            .build();

        assertEquals("testuser", user.getUsername());
        assertEquals("Test User", user.getName());
        assertEquals("testuser@example.com", user.getEmail());
    }

    @Test
    void testLoadSimpleUserFromString() {
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
    void testLoadSimpleUserFromFile() {
        InputStream is = getClass().getResourceAsStream("/com/baeldung/manifold/simpleUserData.json");
        InputStreamReader reader = new InputStreamReader(is);
        SimpleUser user = SimpleUser.load().fromJsonReader(reader);

        assertEquals("testuser", user.getUsername());
        assertEquals("Test User", user.getName());
        assertEquals("testuser@example.com", user.getEmail());
    }

    @Test
    void testGenerateSimpleUserToString() {
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
    void testGenerateSimpleUserToWriter() {
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
    void testGenerateSimpleUserToStringYaml() {
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
    void testGenerateSimpleUserToStringCsv() {
        SimpleUser user = SimpleUser.builder("testuser", "Test User")
            .withEmail("testuser@example.com")
            .build();
        assertEquals("""
            "username","name","email"
            "testuser","Test User","testuser@example.com"
            """, user.write().toCsv());
    }

    @Test
    void testGenerateSimpleUserToStringXml() {
        SimpleUser user = SimpleUser.builder("testuser", "Test User")
            .withEmail("testuser@example.com")
            .build();
        assertEquals("""
            <root_object username="testuser" name="Test User" email="testuser@example.com"/>
            """, user.write().toXml());
    }
}
