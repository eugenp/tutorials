package com.baeldung.boot.jackson.app;

import static com.baeldung.boot.jackson.config.CoffeeConstants.FIXED_DATE;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import com.baeldung.boot.jackson.config.CoffeeConstants;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractCoffeeIntegrationTest {

    @Autowired
    protected TestRestTemplate restTemplate;

    @Test
    public void whenGetCoffee_thenSerializedWithDateAndNonNull() {
        String formattedDate = DateTimeFormatter.ofPattern(CoffeeConstants.DATETIME_FORMAT)
                .format(FIXED_DATE);

        String brand = "Lavazza";
        String url = "/coffee?brand=" + brand;

        String response = restTemplate.getForObject(url, String.class);

        assertThat(response).isEqualTo(
                "{\"brand\":\"" + brand + "\",\"date\":\"" + formattedDate + "\"}");
    }
}
