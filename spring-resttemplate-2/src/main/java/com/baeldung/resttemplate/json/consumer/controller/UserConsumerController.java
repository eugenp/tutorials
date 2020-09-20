package com.baeldung.resttemplate.json.consumer.controller;

import com.baeldung.resttemplate.json.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class UserConsumerController {

    @Autowired
    private final RestTemplate restTemplate;

    public UserConsumerController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    private static final String BASE_URL="http://localhost:8080/users";

    @GetMapping(value="/usersAsListOfObjects")
    public @ResponseBody
    Object[] getUsersAsObjects(){

        ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(BASE_URL, Object[].class);
        Object[] objects = responseEntity.getBody();
        MediaType contentType = responseEntity.getHeaders().getContentType();
        HttpStatus statusCode = responseEntity.getStatusCode();
        return objects;
    }

    @GetMapping(value="/usersAsListOfPojo")
    public @ResponseBody
    List<User> getUsersAsPOJO(){
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(BASE_URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>(){});
        List<User> users = responseEntity.getBody();
        MediaType contentType = responseEntity.getHeaders().getContentType();
        HttpStatus statusCode = responseEntity.getStatusCode();
        return users;
    }

}
