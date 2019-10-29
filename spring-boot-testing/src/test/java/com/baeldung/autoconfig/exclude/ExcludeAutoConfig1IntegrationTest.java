package com.baeldung.autoconfig.exclude;

import static org.junit.Assert.assertEquals;
import io.restassured.RestAssured;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.boot.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@TestPropertySource(properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration")
public class ExcludeAutoConfig1IntegrationTest {

    @Test
    public void givenSecurityConfigExcluded_whenAccessHome_thenNoAuthenticationRequired() {
        int statusCode = RestAssured.get("http://localhost:8080/").statusCode();
        assertEquals(HttpStatus.OK.value(), statusCode);
    }
}
