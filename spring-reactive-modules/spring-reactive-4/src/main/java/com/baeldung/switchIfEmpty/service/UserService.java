package com.baeldung.switchIfEmpty.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.baeldung.switchIfEmpty.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final Map<String, User> usersCache;

    private final ObjectMapper objectMapper;

    public UserService(ObjectMapper objectMapper) {
        this.usersCache = new HashMap<>();
        this.objectMapper = objectMapper;
    }

    public Map<String, User> getUsers() {
        return usersCache;
    }

    public Mono<User> findByUserIdWithDefer(String id) {
        return fetchFromCache(id).switchIfEmpty(Mono.defer(() -> fetchFromFile(id)));
    }

    public Mono<User> findByUserIdWithoutDefer(String id) {
        return fetchFromCache(id).switchIfEmpty(fetchFromFile(id));
    }

    private Mono<User> fetchFromCache(String id) {
        User user = usersCache.get(id);
        if (user != null) {
            LOG.info("Fetched user {} from cache", id);
            return Mono.just(user);
        }
        return Mono.empty();
    }

    private Mono<User> fetchFromFile(String id) {
        try {
            File file = new ClassPathResource("users.json").getFile();
            String usersData = new String(Files.readAllBytes(file.toPath()));
            List<User> users = objectMapper.readValue(usersData, new TypeReference<List<User>>() {
            });
            User user = users.stream()
                .filter(u -> u.getId()
                    .equalsIgnoreCase(id))
                .findFirst()
                .get();
            usersCache.put(user.getId(), user);
            LOG.info("Fetched user {} from file", id);
            return Mono.just(user);
        } catch (IOException e) {
            return Mono.error(e);
        }
    }

}
