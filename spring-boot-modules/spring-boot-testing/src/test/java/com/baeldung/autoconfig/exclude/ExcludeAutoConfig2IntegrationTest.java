package com.baeldung.autoconfig.exclude;

import com.baeldung.boot.Application;
import io.restassured.RestAssured;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ExcludeAutoConfig2IntegrationTest {

    /**
     * Encapsulates the random port the test server is listening on.
     */
    @LocalServerPort
    private int port;

    @Test
    public void givenSecurityConfigExcluded_whenAccessHome_thenNoAuthenticationRequired() {
        int statusCode = RestAssured.get("http://localhost:" + port).statusCode();
        assertEquals(HttpStatus.OK.value(), statusCode);
    }
}
