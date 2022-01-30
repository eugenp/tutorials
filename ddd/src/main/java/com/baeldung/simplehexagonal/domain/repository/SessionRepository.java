package com.baeldung.simplehexagonal.domain.repository;

import java.util.List;

import com.baeldung.simplehexagonal.domain.Session;

public interface SessionRepository {

    List<Session> findAll();

    Session findById(Long id);

    Session save(Session session);

    void deleteById(Long id);

}
