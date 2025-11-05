package com.baeldung.dynamically.updating.properties;

import static org.awaitility.Awaitility.await;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.baeldung.dynamically.updating.properties.services.ExampleBean;
import com.baeldung.dynamically.updating.properties.services.PropertyUpdaterService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PropertyUpdaterServiceUnitTest {

    @Autowired
    private PropertyUpdaterService propertyUpdaterService;

    @Autowired
    private ExampleBean exampleBean;

    @LocalServerPort
    private int port;

    @Test
    @Timeout(5)
    public void whenUpdatingProperty_thenPropertyIsUpdatedAndRefreshed() throws InterruptedException {
        // Injects a new property into the test context
        propertyUpdaterService.updateProperty("my.custom.property", "newValue");

        // Trigger the refresh by calling the actuator endpoint
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity("http://localhost:" + port + "/actuator/refresh", entity, String.class);

        // Awaitility to wait until the property is updated
        await().atMost(5, TimeUnit.SECONDS).until(() -> "newValue".equals(exampleBean.getCustomProperty()));
    }
}
