package com.baeldung.simplehexagonal.infrastructure.repositories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.simplehexagonal.domain.Session;
import com.baeldung.simplehexagonal.domain.repository.SessionRepository;

@Component
public class SessionRepositoryImpl implements SessionRepository {

    @Autowired
    private SessionJpaRepository sessionJpaRepository;

    @Override
    public List<Session> findAll() {
        return sessionJpaRepository.findAll()
            .stream()
            .map(SessionEntity::toSession)
            .collect(Collectors.toList());
    }

    @Override
    public Session findById(Long id) {
        SessionEntity sessionEntity = sessionJpaRepository.getById(id);
        return sessionEntity.toSession();
    }

    @Override
    public Session save(Session session) {
        return sessionJpaRepository.saveAndFlush(new SessionEntity(session))
            .toSession();
    }

    public void deleteById(Long id) {
        sessionJpaRepository.deleteById(id);
    }

}
