package com.baeldung.annotation.servletcomponentscan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringBootAnnotatedApp.class)
public class SpringBootWithServletComponentIntegrationTest {

    @Autowired
    private ServletContext servletContext;

    @Test
    public void givenServletContext_whenAccessAttrs_thenFoundAttrsPutInServletListner() {
        assertNotNull(servletContext);
        assertNotNull(servletContext.getAttribute("servlet-context-attr"));
        assertEquals("test", servletContext.getAttribute("servlet-context-attr"));
    }

    @Test
    public void givenServletContext_whenCheckHelloFilterMappings_thenCorrect() {
        assertNotNull(servletContext);
        FilterRegistration filterRegistration = servletContext.getFilterRegistration("hello filter");

        assertNotNull(filterRegistration);
        assertTrue(filterRegistration.getServletNameMappings().contains("echo servlet"));
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void givenServletFilter_whenGetHello_thenRequestFiltered() {
        ResponseEntity<String> responseEntity = this.restTemplate.getForEntity("/hello", String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("filtering hello", responseEntity.getBody());
    }

    @Test
    public void givenFilterAndServlet_whenPostEcho_thenEchoFiltered() {
        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity("/echo", "echo", String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("filtering echo", responseEntity.getBody());
    }

}
