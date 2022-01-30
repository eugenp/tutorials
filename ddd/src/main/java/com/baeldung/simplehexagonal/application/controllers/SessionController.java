package com.baeldung.simplehexagonal.application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.simplehexagonal.domain.Session;
import com.baeldung.simplehexagonal.domain.services.SessionService;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {

    private SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    public List<Session> findAll() {
        return sessionService.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Session get(@PathVariable Long id) {
        return sessionService.get(id);
    }

    @PostMapping
    public Session create(@RequestBody final Session session) {
        return sessionService.create(session);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        sessionService.delete(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Session update(@PathVariable Long id, @RequestBody Session session) {
        return sessionService.update(id, session);
    }

}
