package com.baeldung.serverport;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GetServerPortApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("fixedport")
public class GetServerFixedPortUnitTest {
    private final static int EXPECTED_PORT = 7777;

    @Value("${server.port}")
    private int serverPort;

    @Autowired
    private ServerProperties serverProperties;

    @Test
    public void givenFixedPortAsServerPort_whenReadServerPort_thenGetThePort() {
        assertEquals("Reading fixed port by @Value(\"${server.port}\") will get the port.", EXPECTED_PORT, serverPort);
    }

    @Test
    public void givenFixedPortAsServerPort_whenReadServerProps_thenGetThePort() {
        int port = serverProperties.getPort();
        assertEquals("Reading fixed port from serverProperties will get the port.", EXPECTED_PORT, port);
    }

}
