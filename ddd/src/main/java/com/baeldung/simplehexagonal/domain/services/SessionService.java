package com.baeldung.simplehexagonal.domain.services;

import java.util.List;

import com.baeldung.simplehexagonal.domain.Session;

public interface SessionService {

    List<Session> findAll();

    Session get(Long id);

    Session create(Session session);

    void delete(Long id);

    Session update(Long id, Session session);

}