package com.baeldung.resttemplate.https.client;

import static java.util.Arrays.asList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.baeldung.resttemplate.https.client.config.HttpsClientConfig;
import com.baeldung.resttemplate.lists.dto.Employee;

public class SecureRestTemplateClient {

    @Autowired
    Environment env;

    public static void main(String[] args) {
        SecureRestTemplateClient secureRestClient = new SecureRestTemplateClient();

        HttpsClientConfig secureRestTemplateConfig = new HttpsClientConfig();
        RestTemplate restTemplateWithoutSsl = secureRestTemplateConfig.restTemplateWithoutSsl();
        RestTemplate restTemplateWithSsl = secureRestTemplateConfig.restTemplateWithSsl();
        RestTemplate restTemplateWithProps = secureRestTemplateConfig.restTemplate(secureRestClient.env);

        System.out.println("Calling HTTPS GET by passing SSL");
        secureRestClient.consumeWithRestTemplate(restTemplateWithoutSsl);

        System.out.println("Calling HTTPS GET after validating SSL");
        secureRestClient.consumeWithRestTemplate(restTemplateWithSsl);

        System.out.println("Calling HTTPS GET after setting SSL truststore in system properties");
        secureRestClient.consumeWithRestTemplate(restTemplateWithProps);
    }

    private Employee[] consumeWithRestTemplate(RestTemplate restTemplate) {
        ResponseEntity<Employee[]> response = restTemplate.exchange("http://localhost:8082/spring-rest/employees/", HttpMethod.GET, null, Employee[].class);
        Employee[] employees = response.getBody();

        assert employees != null;
        asList(employees).forEach(System.out::println);

        return employees;
    }
}
