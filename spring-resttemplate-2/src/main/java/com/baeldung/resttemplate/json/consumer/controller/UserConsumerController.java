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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserConsumerController {

    private static final String BASE_URL="http://localhost:8080/users";
    private final RestTemplate restTemplate;

    public UserConsumerController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping(value="/usersAsArrayOfObjects")
    public @ResponseBody
    Object[] getUsersAsObjects(){

        ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(BASE_URL, Object[].class);
        Object[] objects = responseEntity.getBody();
        MediaType contentType = responseEntity.getHeaders().getContentType();
        HttpStatus statusCode = responseEntity.getStatusCode();
        return objects;
    }


    @GetMapping(value="/usersAsArrayOfPOJO")
    public @ResponseBody
    List<String> getPostCodesFromUserArray(){

        ResponseEntity<User[]> responseEntity = restTemplate.getForEntity(BASE_URL, User[].class);
        User[] userArray = responseEntity.getBody();
        MediaType contentType = responseEntity.getHeaders().getContentType();
        HttpStatus statusCode = responseEntity.getStatusCode();
        return Arrays.stream(userArray).flatMap(user->user.getAddressList().stream()).map(address->address.getPostCode()).collect(Collectors.toList());
    }

    @GetMapping(value="/usersAsListOfPojo")
    public @ResponseBody
    List<String> getUserNames(){
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(BASE_URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>(){});
        List<User> users = responseEntity.getBody();
        return users.stream().map(user->user.getName()).collect(Collectors.toList());
    }
}
