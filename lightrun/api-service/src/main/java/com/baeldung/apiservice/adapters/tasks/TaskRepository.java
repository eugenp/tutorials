package com.baeldung.apiservice.adapters.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Repository
public class TaskRepository {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${tasks-service.url}")
    private String tasksService;

    public Task getTaskById(String id) {
        var uri = UriComponentsBuilder.fromUriString(tasksService)
                .path(id)
                .build()
                .toUri();

        try {
            return restTemplate.getForObject(uri, Task.class);
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }
}
