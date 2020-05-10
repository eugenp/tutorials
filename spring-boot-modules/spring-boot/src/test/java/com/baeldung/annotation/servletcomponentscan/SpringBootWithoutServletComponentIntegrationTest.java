package com.baeldung.annotation.servletcomponentscan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringBootPlainApp.class)
public class SpringBootWithoutServletComponentIntegrationTest {

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void givenServletContext_whenAccessAttrs_thenNotFound() {
        assertNull(servletContext.getAttribute("servlet-context-attr"));
    }

    @Test
    public void givenServletFilter_whenGetHello_thenEndpointNotFound() {
        ResponseEntity<String> responseEntity = this.restTemplate.getForEntity("/hello", String.class);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void givenServletContext_whenCheckFilterMappings_thenEmpty() {
        assertNotNull(servletContext);
        FilterRegistration filterRegistration = servletContext.getFilterRegistration("hello filter");

        assertNull(filterRegistration);
    }

}
