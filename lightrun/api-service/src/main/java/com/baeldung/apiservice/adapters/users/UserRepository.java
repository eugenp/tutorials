package com.baeldung.apiservice.adapters.users;

import com.baeldung.apiservice.adapters.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Repository
public class UserRepository {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${users-service.url}")
    private String usersService;

    public User getUserById(String id) {
        var uri = UriComponentsBuilder.fromUriString(usersService)
                .path(id)
                .build()
                .toUri();

        try {
            return restTemplate.getForObject(uri, User.class);
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }
}
