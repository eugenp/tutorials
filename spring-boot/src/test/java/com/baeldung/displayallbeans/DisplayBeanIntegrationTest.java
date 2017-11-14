package com.baeldung.displayallbeans;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.BDDAssertions.then;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "management.port=0", "endpoints.beans.id=springbeans", "endpoints.beans.sensitive=false" })
public class DisplayBeanIntegrationTest {

    @LocalServerPort
    private int port;

    @Value("${local.management.port}")
    private int mgt;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private WebApplicationContext context;

    @Test
    public void givenRestTemplate_whenAccessServerUrl_thenHttpStatusOK() throws Exception {
        ResponseEntity<String> entity = this.testRestTemplate.getForEntity("http://localhost:" + this.port + "/displayallbeans", String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void givenRestTemplate_whenAccessEndpointUrl_thenHttpStatusOK() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<List> entity = this.testRestTemplate.getForEntity("http://localhost:" + this.mgt + "/springbeans", List.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void givenRestTemplate_whenAccessEndpointUrl_thenReturnsBeanNames() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<List> entity = this.testRestTemplate.getForEntity("http://localhost:" + this.mgt + "/springbeans", List.class);

        List<Map<String, Object>> allBeans = (List) ((Map) entity.getBody()
            .get(0)).get("beans");
        List<String> beanNamesList = allBeans.stream()
            .map(x -> (String) x.get("bean"))
            .collect(Collectors.toList());

        assertThat(beanNamesList, hasItem("fooController"));
        assertThat(beanNamesList, hasItem("fooService"));
    }

    @Test
    public void givenWebApplicationContext_whenAccessGetBeanDefinitionNames_thenReturnsBeanNames() throws Exception {
        String[] beanNames = context.getBeanDefinitionNames();

        List<String> beanNamesList = Arrays.asList(beanNames);
        assertTrue(beanNamesList.contains("fooController"));
        assertTrue(beanNamesList.contains("fooService"));
    }
}
