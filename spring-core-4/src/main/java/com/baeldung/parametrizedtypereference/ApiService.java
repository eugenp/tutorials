package com.baeldung.parametrizedtypereference;

import static com.baeldung.parametrizedtypereference.TypeReferences.USER_LIST;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public ApiService(RestTemplate restTemplate, String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public List<User> fetchUserList() {
        ParameterizedTypeReference<List<User>> typeRef = new ParameterizedTypeReference<List<User>>() {
        };

        ResponseEntity<List<User>> response = restTemplate.exchange(baseUrl + "/api/users", HttpMethod.GET, null, typeRef);

        return response.getBody();
    }

    public List<User> fetchUsersWrongApproach() {
        ResponseEntity<List> response = restTemplate.getForEntity(baseUrl + "/api/users", List.class);

        return (List<User>) response.getBody();
    }

    public List<User> fetchUsersCorrectApproach() {
        ParameterizedTypeReference<List<User>> typeRef = new ParameterizedTypeReference<List<User>>() {
        };

        ResponseEntity<List<User>> response = restTemplate.exchange(baseUrl + "/api/users", HttpMethod.GET, null, typeRef);

        return response.getBody();
    }

    public User fetchUser(Long id) {
        return restTemplate.getForObject(baseUrl + "/api/users/" + id, User.class);
    }

    public User[] fetchUsersArray() {
        return restTemplate.getForObject(baseUrl + "/api/users", User[].class);
    }

    public List<User> fetchUsersList() {
        ParameterizedTypeReference<List<User>> typeRef = new ParameterizedTypeReference<List<User>>() {
        };

        ResponseEntity<List<User>> response = restTemplate.exchange(baseUrl + "/api/users", HttpMethod.GET, null, typeRef);

        return response.getBody();
    }

    public List<User> fetchUsersListWithExistingReference() {
        ResponseEntity<List<User>> response = restTemplate.exchange(baseUrl + "/api/users", HttpMethod.GET, null, USER_LIST);

        return response.getBody();
    }
}