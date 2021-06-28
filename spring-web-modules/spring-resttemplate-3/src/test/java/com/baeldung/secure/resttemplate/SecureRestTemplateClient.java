package com.baeldung.secure.resttemplate;

import static java.util.Arrays.asList;

import com.baeldung.resttemplate.lists.EmployeeApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.baeldung.resttemplate.https.client.config.HttpsClientConfig;
import com.baeldung.resttemplate.lists.dto.Employee;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = EmployeeApplication.class)
@Import({HttpsClientConfig.class})
class SecureRestTemplateClient {

    @Mock Environment env;

    HttpsClientConfig secureRestTemplateConfig;
    String uri;
    
       
    @BeforeEach
    public void init(){
        secureRestTemplateConfig = new HttpsClientConfig();
        env = Mockito.mock(Environment.class);     
        when(env.getProperty("server.ssl.trust-store"))
          .thenReturn("classpath:baeldung.p12");  
        when(env.getProperty("server.ssl.trust-store-password"))
        .thenReturn("baeldung");

        uri = "https://localhost:8082/spring-rest/employees/";
    }


    @Test
    @DisplayName("Calling HTTPS GET by-passing SSL")
    void givenRestTemplate_whenSslDisabled_thenSuccess() {
        assertNotNull(uri);
        RestTemplate restTemplateWithoutSsl = secureRestTemplateConfig.restTemplateWithoutSsl();
        consumeWithRestTemplate(restTemplateWithoutSsl, uri, null, List.class);
    }

    @Test
    @DisplayName("Calling HTTPS GET after validating SSL")
    void givenRestTemplate_whenSslValid_thenSuccess() {
        RestTemplate restTemplateWithSsl = secureRestTemplateConfig.restTemplateWithSsl();
        consumeWithRestTemplate(restTemplateWithSsl, uri, null, List.class);
    }
    
    @Test
    @DisplayName("Calling HTTPS GET after setting SSL truststore in system properties")
    void givenRestTemplate_whenSslInProps_thenSuccess() {
        RestTemplate restTemplateWithProps = secureRestTemplateConfig.restTemplate(env);
        consumeWithRestTemplate(restTemplateWithProps, uri, null, List.class);
    }

    @Test
    @DisplayName("Calling HTTPS GET with Simple RestTemplate")
    void givenRestTemplate_withSimpleRestTemplate_thenFail() {
        RestTemplate restTemplate = new RestTemplate();
        assertThrows(ResourceAccessException.class, ()->consumeWithRestTemplate(restTemplate, uri, null, List.class));
    }

    @Test
    @DisplayName("Calling HTTP GET")
    void givenRestTemplate_withHTTP_thenFail() {
        uri = "http://localhost:8082/spring-rest/employees/";
        RestTemplate restTemplateWithoutSsl = secureRestTemplateConfig.restTemplateWithoutSsl();
        assertThrows(HttpClientErrorException.class, ()->consumeWithRestTemplate(restTemplateWithoutSsl, uri, null, List.class));
    }

    private List<Employee> consumeWithRestTemplate(RestTemplate restTemplate, String uri, HttpEntity request, Class reqClass) {
        ResponseEntity response = restTemplate.exchange(uri, HttpMethod.GET, request, reqClass);
        List<Employee> employees = (List<Employee>) response.getBody();
        assert employees != null;
        asList(employees).forEach(System.out::println);

        return employees;
    }
}
