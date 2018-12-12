package com.baeldung.displayallbeans;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;

import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.beans.BeansEndpoint.ContextBeans;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "management.port=0", "management.endpoints.web.exposure.include=*" })
public class DisplayBeanIntegrationTest {

    @LocalServerPort
    private int port;

    @Value("${local.management.port}")
    private int mgt;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private WebApplicationContext context;

    private static final String ACTUATOR_PATH = "/actuator";

    @Test
    public void givenRestTemplate_whenAccessServerUrl_thenHttpStatusOK() throws Exception {
        ResponseEntity<String> entity = this.testRestTemplate.getForEntity("http://localhost:" + this.port + "/displayallbeans", String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void givenRestTemplate_whenAccessEndpointUrl_thenHttpStatusOK() throws Exception {
        ParameterizedTypeReference<Map<String, ContextBeans>> responseType = new ParameterizedTypeReference<Map<String, ContextBeans>>() {
        };
        RequestEntity<Void> requestEntity = RequestEntity.get(new URI("http://localhost:" + this.mgt + ACTUATOR_PATH + "/beans"))
            .accept(MediaType.APPLICATION_JSON)
            .build();
        ResponseEntity<Map<String, ContextBeans>> entity = this.testRestTemplate.exchange(requestEntity, responseType);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void givenRestTemplate_whenAccessEndpointUrl_thenReturnsBeanNames() throws Exception {
        RequestEntity<Void> requestEntity = RequestEntity.get(new URI("http://localhost:" + this.mgt + ACTUATOR_PATH + "/beans"))
            .accept(MediaType.APPLICATION_JSON)
            .build();
        ResponseEntity<BeanActuatorResponse> entity = this.testRestTemplate.exchange(requestEntity, BeanActuatorResponse.class);

        Collection<String> beanNamesList = entity.getBody()
            .getBeans();

        assertThat(beanNamesList).contains("fooController", "fooService");
    }

    @Test
    public void givenWebApplicationContext_whenAccessGetBeanDefinitionNames_thenReturnsBeanNames() throws Exception {
        String[] beanNames = context.getBeanDefinitionNames();

        List<String> beanNamesList = Arrays.asList(beanNames);
        assertThat(beanNamesList).contains("fooController", "fooService");
    }

    private static class BeanActuatorResponse {
        private Map<String, Map<String, Map<String, Map<String, Object>>>> contexts;

        public Collection<String> getBeans() {
            return this.contexts.get("application")
                .get("beans")
                .keySet();
        }

        public Map<String, Map<String, Map<String, Map<String, Object>>>> getContexts() {
            return contexts;
        }
    }
}
