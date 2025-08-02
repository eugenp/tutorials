package com.baeldung.sharedthreadvars.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class RestApi {

    private final Logger log = LoggerFactory.getLogger(RestApi.class);

    private Map<UUID, String> items = new HashMap<>();

    @PostMapping
    public String post(@RequestBody String name) {
        UUID uuid = UUID.randomUUID();
        items.put(uuid, name);

        log.debug("put {}={}", uuid, name);
        return uuid.toString();
    }

    @GetMapping
    public String get(@RequestParam("uuid") String uuid) {
        String name = items.remove(UUID.fromString(uuid));

        if (name == null)
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED);

        log.debug("get {}={}", uuid, name);
        return name;
    }
}
