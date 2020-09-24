package com.baeldung.resttemplate.json.consumer.service;

import com.baeldung.resttemplate.json.model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserConsumerServiceImpl implements UserConsumerService{

    private static final String BASE_URL = "http://localhost:8080/users";
    private final RestTemplate restTemplate;

    public UserConsumerServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Object[] getUsersAsObjectArray() {
        ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(BASE_URL, Object[].class);
        Object[] objects = responseEntity.getBody();
        return objects;
    }

    @Override
    public List<String> processUserDataFromUserArray() {
        ResponseEntity<User[]> responseEntity = restTemplate.getForEntity(BASE_URL, User[].class);
        User[] userArray = responseEntity.getBody();
        return Arrays.stream(userArray).map(user -> user.getName()).collect(Collectors.toList());
    }

    @Override
    public List<String> processUserDataFromUserList() {
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(BASE_URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {});
        List<User> users = responseEntity.getBody();
        return users.stream().map(user -> user.getName()).collect(Collectors.toList());
    }

    @Override
    public List<String> processNestedUserDataFromUserArray() {
        ResponseEntity<User[]> responseEntity = restTemplate.getForEntity(BASE_URL, User[].class);
        User[] userArray = responseEntity.getBody();
        MediaType contentType = responseEntity.getHeaders().getContentType();
        HttpStatus statusCode = responseEntity.getStatusCode();
        return Arrays.stream(userArray).flatMap(user -> user.getAddressList().stream()).map(address -> address.getPostCode()).collect(Collectors.toList());
    }

    @Override
    public List<String> processNestedUserDataFromUserList() {
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(BASE_URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
        });
        List<User> userList = responseEntity.getBody();
        return userList.stream().flatMap(user -> user.getAddressList().stream()).map(address -> address.getPostCode()).collect(Collectors.toList());
    }
}