package com.baeldung.spring.cloud.ribbon.client;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@SuppressWarnings("unused")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerLocationApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServerLocationAppIntegrationTest {
    ConfigurableApplicationContext application2;
    ConfigurableApplicationContext application3;

    @Before
    public void startApps() {
        this.application2 = startApp(9092);
        this.application3 = startApp(9999);
    }

    @After
    public void closeApps() {
        this.application2.close();
        this.application3.close();
    }

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void loadBalancingServersTest() {
        ResponseEntity<String> response = this.testRestTemplate.getForEntity("http://localhost:" + this.port + "/server-location", String.class);
        assertEquals(response.getBody(), "Australia");
    }

    private ConfigurableApplicationContext startApp(int port) {
        return SpringApplication.run(TestConfig.class, "--server.port=" + port, "--spring.jmx.enabled=false");
    }
}
