package org.baeldung.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = MainConfig.class)
@TestPropertySource(properties = { "security.basic.enabled=false" })
public class ActuatorInfoIntegrationTest {

    @Autowired 
    private TestRestTemplate restTemplate;

    @Test
    public void whenGetInfo_thenAdditionalInfoReturned() throws IOException {
        final String expectedResponse = new String(Files.readAllBytes(Paths.get("src/test/resources/expectedResponse.json")));
        final ResponseEntity<String> responseEntity = this.restTemplate.getForEntity("/info", String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }
}