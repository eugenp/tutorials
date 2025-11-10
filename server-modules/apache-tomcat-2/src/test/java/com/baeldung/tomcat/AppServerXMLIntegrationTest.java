package com.baeldung.tomcat;

import org.apache.catalina.startup.Catalina;
import org.junit.jupiter.api.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.baeldung.tomcat.HttpConnection.getContent;
import static com.baeldung.tomcat.HttpConnection.getResponseCode;
import static org.junit.jupiter.api.Assertions.*;

public class AppServerXMLIntegrationTest {

    private static AppServerXML app;
    private static Catalina catalina;
    private static final int HTTP_PORT_1 = 8081;
    private static final int HTTP_PORT_2 = 7081;

    @BeforeAll
    static void setUp() throws Exception {
        app = new AppServerXML();
        catalina = app.startServer();
        Thread.sleep(2000);
    }

    @AfterAll
    static void shutDown() throws Exception {
        if (catalina != null && catalina.getServer() != null) {
            catalina.stop();
            Thread.sleep(1000);
        }
    }

    @Test
    void givenMultipleConnectors_whenServerStarts_thenContainsMultiplePorts() {
        assertNotNull(catalina.getServer(), "Server should be initialized");
        
        Path configFile = Paths.get("target/tomcat-base/server.xml");
        assertTrue(Files.exists(configFile), "Generated server.xml should exist");
        
        assertDoesNotThrow(() -> {
            String config = Files.readString(configFile);
            assertTrue(config.contains("port=\"8081\""), "Config should have port 8081");
            assertTrue(config.contains("port=\"7081\""), "Config should have port 7081");
            assertFalse(config.contains("STATIC_DIR_PLACEHOLDER"), "Placeholder should be replaced");
        });
    }

    @Test
    void givenMultipleConnectors_whenResponds_thenReturns200() {
        assertDoesNotThrow(() -> {
            int response1 = getResponseCode(HTTP_PORT_1);
            int response2 = getResponseCode(HTTP_PORT_2);
            
            assertEquals(200, response1, "Port 8081 should respond with 200 OK");
            assertEquals(200, response2, "Port 7081 should respond with 200 OK");
        });
    }

    @Test
    void givenMultipleConnectors_whenResponds_thenReturnsIdenticalContent() {
        assertDoesNotThrow(() -> {
            String content1 = getContent(HTTP_PORT_1);
            String content2 = getContent(HTTP_PORT_2);

            assertNotNull(content1, "Content from port 8081 should not be null");
            assertNotNull(content2, "Content from port 7081 should not be null");
            
            assertTrue(content1.contains("Tomcat is running"), "Content should contain expected text");
            assertEquals(content1, content2, "Both ports should serve identical content");
        });
    }
}

