package com.baeldung.spring.cloudfunction.aws;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CloudFunctionApplicationUnitTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void givenAString_whenReverseStringCloudFunctionInvoked_thenStringIsReversed() {  
        assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/reverseString/HelloWorld", String.class)).isEqualTo("dlroWolleH");
    }

    @Test
    public void givenAString_whenGreeterCloudFunctionInvoked_thenPrintsGreeting() {        	  
        assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/greeter/BaeldungUser", String.class)).isEqualTo("Hello BaeldungUser, and welcome to Spring Cloud Function!!!");
    }

}
