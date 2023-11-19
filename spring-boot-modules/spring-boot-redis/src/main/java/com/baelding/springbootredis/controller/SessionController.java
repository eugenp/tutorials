package com.baelding.springbootredis.controller;

import com.baelding.springbootredis.dto.SessionCreateRequest;
import com.baelding.springbootredis.model.Session;
import com.baelding.springbootredis.service.cache.session.SessionCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/sessions")
public class SessionController {
    private static final String LOCATION_HEADER_VALUE_FORMAT = "/v1/sessions/%s";

    @Autowired
    private SessionCache sessionCache;

    @GetMapping
    public List<Session> getAllSessions() {
        return sessionCache.getAllSessions();
    }

    @GetMapping("/{id}")
    public Session getSession(@PathVariable("id") String id) {
        return sessionCache.getSession(id);
    }

    @PostMapping
    public ResponseEntity<Session> createASession(@RequestBody SessionCreateRequest sessionCreateRequest) {
        Session createdSession = sessionCache.createASession(Session.builder().expirationInSeconds(sessionCreateRequest.getExpirationInSeconds()).build());

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.set(HttpHeaders.LOCATION, String.format(LOCATION_HEADER_VALUE_FORMAT, createdSession.getId()));

        return new ResponseEntity<>(createdSession, new HttpHeaders(headers), HttpStatus.CREATED);
    }
}
