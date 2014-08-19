package org.baeldung.client;

import org.baeldung.client.spring.ClientConfig;
import org.baeldung.web.dto.Foo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ClientConfig.class }, loader = AnnotationConfigContextLoader.class)
public class ClientWithSpringLiveTest {

    @Autowired
    private RestTemplate restTemplate;

    // tests - no Spring

    @Test
    public final void whenSecuredRestApiIsConsumed_then200OK() {
        final String uri = "http://localhost:8080/spring-security-rest-digest-auth/api/foos/1";
        final ResponseEntity<Foo> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, null, Foo.class);

        System.out.println(responseEntity.getStatusCode());
    }

}
