package com.baeldung.resttemplateexception;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.baeldung.resttemplateexception.model.Product;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { RestTemplate.class, RestTemplateExceptionApplication.class })
public class RestTemplateExceptionLiveTest {

    @Autowired
    RestTemplate restTemplate;

    @Test(expected = IllegalArgumentException.class)
    public void givenGetUrl_whenJsonIsPassed_thenThrowException() {

        String url = "http://localhost:8080/spring-rest/api/get?criterion={\"prop\":\"name\",\"value\":\"ASUS VivoBook\"}";
        Product product = restTemplate.getForObject(url, Product.class);

    }

    @Test
    public void givenGetUrl_whenJsonIsPassed_thenGetProduct() {

        String criterion = "{\"prop\":\"name\",\"value\":\"ASUS VivoBook\"}";
        String url = "http://localhost:8080/spring-rest/api/get?criterion={criterion}";
        Product product = restTemplate.getForObject(url, Product.class, criterion);

        assertEquals(product.getPrice(), 650, 0);

    }

    @Test
    public void givenGetUrl_whenJsonIsPassed_thenReturnProduct() {

        String criterion = "{\"prop\":\"name\",\"value\":\"Acer Aspire 5\"}";
        String url = "http://localhost:8080/spring-rest/api/get";

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url).queryParam("criterion", criterion);
        Product product = restTemplate.getForObject(builder.build().toUri(), Product.class);

        assertEquals(product.getId(), 1, 0);

    }
}
