package com.baeldung.parametrizedtypereference;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ApiService {

    private final RestTemplate restTemplate;

    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<User> fetchUserList() {
        ParameterizedTypeReference<List<User>> typeRef =
                new ParameterizedTypeReference<List<User>>() {};

        ResponseEntity<List<User>> response = restTemplate.exchange(
                "/api/users",
                HttpMethod.GET,
                null,
                typeRef
        );

        return response.getBody();
    }

    public List<User> createUsers(List<User> users) {
        ParameterizedTypeReference<List<User>> typeRef =
                new ParameterizedTypeReference<List<User>>() {};

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<User>> requestEntity = new HttpEntity<>(users, headers);

        ResponseEntity<List<User>> response = restTemplate.exchange(
                "/api/users/batch",
                HttpMethod.POST,
                requestEntity,
                typeRef
        );

        return response.getBody();
    }

    public List<User> fetchUsersWrongApproach() {
        ResponseEntity<List> response = restTemplate.getForEntity(
                "/api/users",
                List.class
        );

        return (List<User>) response.getBody();
    }

    public List<User> fetchUsersCorrectApproach() {
        ParameterizedTypeReference<List<User>> typeRef =
                new ParameterizedTypeReference<List<User>>() {};

        ResponseEntity<List<User>> response = restTemplate.exchange(
                "/api/users",
                HttpMethod.GET,
                null,
                typeRef
        );

        return response.getBody();
    }

    public User fetchUser(Long id) {
        return restTemplate.getForObject("/api/users/" + id, User.class);
    }

    public User[] fetchUsersArray() {
        return restTemplate.getForObject("/api/users", User[].class);
    }

    public List<User> fetchUsersList() {
        ParameterizedTypeReference<List<User>> typeRef =
                new ParameterizedTypeReference<List<User>>() {};

        ResponseEntity<List<User>> response = restTemplate.exchange(
                "/api/users",
                HttpMethod.GET,
                null,
                typeRef
        );

        return response.getBody();
    }

    public List<User> fetchUsersWithErrorHandling() {
        try {
            ResponseEntity<List<User>> response = restTemplate.exchange(
                    "/api/users",
                    HttpMethod.GET,
                    null,
                    TypeReferences.USER_LIST
            );
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new ApiException("HTTP error: " + e.getMessage(), e);
        } catch (ResourceAccessException e) {
            throw new ApiException("Network error: " + e.getMessage(), e);
        }
    }
}
