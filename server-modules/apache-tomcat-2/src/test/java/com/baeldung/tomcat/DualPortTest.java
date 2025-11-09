package com.baeldung.tomcat;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DualPortTest {

    private static DualPort app;
    private static Tomcat tomcat;
    private static final int PORT_1 = 8080;
    private static final int PORT_2 = 7080;

    @BeforeAll
    static void setUp() throws Exception {
        app = new DualPort();
        tomcat = app.startServer();
        Thread.sleep(2000); // Wait for server to fully start
    }

    @AfterAll
    static void tearDown() throws Exception {
        if (tomcat != null && tomcat.getServer() != null) {
            tomcat.stop();
            tomcat.destroy();
            Thread.sleep(1000);
        }
    }

    @Test
    @Order(1)
    @DisplayName("Should start Tomcat with two connectors programmatically")
    void testServerStartsWithTwoConnectors() {
        assertNotNull(tomcat, "Tomcat instance should not be null");
        assertNotNull(tomcat.getServer(), "Server should be initialized");
        
        // Verify two connectors are configured
        Connector[] connectors = tomcat.getService().findConnectors();
        assertEquals(2, connectors.length, "Should have exactly 2 connectors");
        
        // Verify ports
        int[] ports = new int[]{connectors[0].getPort(), connectors[1].getPort()};
        assertTrue(contains(ports, 8080), "Should have connector on port 8080");
        assertTrue(contains(ports, 7080), "Should have connector on port 7080");
    }

    @Test
    @Order(2)
    @DisplayName("Both connectors should respond with HTTP 200")
    void testBothConnectorsRespond() {
        assertDoesNotThrow(() -> {
            int response1 = getResponseCode(PORT_1);
            int response2 = getResponseCode(PORT_2);
            
            assertEquals(200, response1, "Port 8080 should respond with 200 OK");
            assertEquals(200, response2, "Port 7080 should respond with 200 OK");
        });
    }

    @Test
    @Order(3)
    @DisplayName("Each connector should report its own port number")
    void testEachConnectorReportsCorrectPort() {
        assertDoesNotThrow(() -> {
            String content1 = getContent(PORT_1);
            String content2 = getContent(PORT_2);

            assertNotNull(content1, "Content from port 8080 should not be null");
            assertNotNull(content2, "Content from port 7080 should not be null");
            
            // Each port should report its own port number
            assertTrue(content1.contains("Port: 8080"), 
                "Port 8080 should report 'Port: 8080', but got: " + content1);
            assertTrue(content2.contains("Port: 7080"), 
                "Port 7080 should report 'Port: 7080', but got: " + content2);
            
            // Content should be different (showing different ports)
            assertNotEquals(content1, content2, 
                "Each port should report its own port number - content should differ");
        });
    }

    @Test
    @Order(4)
    @DisplayName("Servlet should return plain text content type")
    void testServletContentType() {
        assertDoesNotThrow(() -> {
            URL url = URI.create("http://localhost:" + PORT_1 + "/").toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            
            try {
                String contentType = connection.getContentType();
                assertNotNull(contentType, "Content type should not be null");
                assertTrue(contentType.contains("text/plain"), 
                    "Content type should be text/plain, but got: " + contentType);
            } finally {
                connection.disconnect();
            }
        });
    }

    // Helper methods
    private boolean contains(int[] array, int value) {
        for (int i : array) {
            if (i == value) return true;
        }
        return false;
    }

    private int getResponseCode(int port) throws Exception {
        URL url = URI.create("http://localhost:" + port + "/").toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        
        try {
            return connection.getResponseCode();
        } finally {
            connection.disconnect();
        }
    }

    private String getContent(int port) throws Exception {
        URL url = URI.create("http://localhost:" + port + "/").toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream()))) {
            return reader.lines().collect(Collectors.joining());
        } finally {
            connection.disconnect();
        }
    }
}

