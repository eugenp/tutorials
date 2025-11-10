package com.baeldung.tomcat;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.*;

import static com.baeldung.tomcat.HttpConnection.getContent;
import static com.baeldung.tomcat.HttpConnection.getResponseCode;
import static org.junit.jupiter.api.Assertions.*;

class DualPortIntegrationTest {

    private static DualPort app;
    private static Tomcat tomcat;
    private static final int PORT_1 = 8080;
    private static final int PORT_2 = 7080;

    @BeforeAll
    static void setUp() throws Exception {
        app = new DualPort();
        tomcat = app.startServer();
        Thread.sleep(2000);
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
    void givenMultipleConnectors_whenServerStarts_thenContainsMultiplePorts() {
        assertNotNull(tomcat, "Tomcat instance should not be null");
        assertNotNull(tomcat.getServer(), "Server should be initialized");
        
        Connector[] connectors = tomcat.getService().findConnectors();
        assertEquals(2, connectors.length, "Should have exactly 2 connectors");
        
        int[] ports = new int[]{connectors[0].getPort(), connectors[1].getPort()};
        assertTrue(contains(ports, 8080), "Should have connector on port 8080");
        assertTrue(contains(ports, 7080), "Should have connector on port 7080");
    }

    @Test
    void givenMultipleConnectors_whenResponds_thenReturns200() {
        assertDoesNotThrow(() -> {
            int response1 = getResponseCode(PORT_1);
            int response2 = getResponseCode(PORT_2);
            
            assertEquals(200, response1, "Port 8080 should respond with 200 OK");
            assertEquals(200, response2, "Port 7080 should respond with 200 OK");
        });
    }

    @Test
    void givenMultipleConnectors_whenResponds_thenReturnsCorrectPort() {
        assertDoesNotThrow(() -> {
            String content1 = getContent(PORT_1);
            String content2 = getContent(PORT_2);

            assertNotNull(content1, "Content from port 8080 should not be null");
            assertNotNull(content2, "Content from port 7080 should not be null");
            
            assertTrue(content1.contains("Port: 8080"), "Port 8080 should report 'Port: 8080', but got: " + content1);
            assertTrue(content2.contains("Port: 7080"), "Port 7080 should report 'Port: 7080', but got: " + content2);
            assertNotEquals(content1, content2, "Each port should report its own port number - content should differ");
        });
    }

    private boolean contains(int[] array, int value) {
        for (int i : array) {
            if (i == value) return true;
        }
        return false;
    }
}

