package com.baeldung.serverport;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GetServerPortApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("randomport")
public class GetServerRandomPortUnitTest {

    @Value("${server.port}")
    private int randomServerPort;

    @Autowired
    private ServerPortService serverPortService;

    @Autowired
    private ServerProperties serverProperties;

    @Autowired
    private ServletWebServerApplicationContext webServerAppCtxt;

    @Test
    public void given0AsServerPort_whenReadServerPort_thenGet0() {
        assertEquals("Reading random port by @Value(\"${server.port}\") will get 0.", 0, randomServerPort);
    }

    @Test
    public void given0AsServerPort_whenReadServerProps_thenGet0() {
        int port = serverProperties.getPort();
        assertEquals("Reading random port by serverProperties will get 0.", 0, port);
    }

    @Test
    public void given0AsServerPort_whenReadWebAppCtxt_thenGetThePort() {
        int port = webServerAppCtxt.getWebServer().getPort();
        assertTrue("The random port should be greater than 1023", port > 1023);
    }

    @Test
    public void given0AsServerPort_whenReadFromListener_thenGetThePort() {
        int port = serverPortService.getPort();
        assertTrue("The random port should be greater than 1023", port > 1023);
    }
}
