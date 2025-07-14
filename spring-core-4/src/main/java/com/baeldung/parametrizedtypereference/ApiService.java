package com.baeldung.parametrizedtypereference;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
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
    private final String baseUrl;

    public ApiService(RestTemplate restTemplate, String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public List<User> fetchUserList() {
        ParameterizedTypeReference<List<User>> typeRef =
                new ParameterizedTypeReference<List<User>>() {};

        ResponseEntity<List<User>> response = restTemplate.exchange(
                baseUrl + "/api/users",
                HttpMethod.GET,
                null,
                typeRef
        );

        return response.getBody();
    }

    public List<User> fetchUsersWrongApproach() {
        ResponseEntity<List> response = restTemplate.getForEntity(
                baseUrl + "/api/users",
                List.class
        );

        return (List<User>) response.getBody();
    }

    public List<User> fetchUsersCorrectApproach() {
        ParameterizedTypeReference<List<User>> typeRef =
                new ParameterizedTypeReference<List<User>>() {};

        ResponseEntity<List<User>> response = restTemplate.exchange(
                baseUrl + "/api/users",
                HttpMethod.GET,
                null,
                typeRef
        );

        return response.getBody();
    }

    public User fetchUser(Long id) {
        return restTemplate.getForObject(baseUrl + "/api/users/" + id, User.class);
    }

    public User[] fetchUsersArray() {
        return restTemplate.getForObject(baseUrl + "/api/users", User[].class);
    }

    public List<User> fetchUsersList() {
        ParameterizedTypeReference<List<User>> typeRef =
                new ParameterizedTypeReference<List<User>>() {};

        ResponseEntity<List<User>> response = restTemplate.exchange(
                baseUrl + "/api/users",
                HttpMethod.GET,
                null,
                typeRef
        );

        return response.getBody();
    }

    public List<User> fetchUsersWithErrorHandling() {
        try {
            ResponseEntity<List<User>> response = restTemplate.exchange(
                    baseUrl + "/api/users",
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