package com.baelding.springbootredis.service.cache.session;

import com.baelding.springbootredis.exception.session.SessionNotFoundException;
import com.baelding.springbootredis.model.Session;
import com.baelding.springbootredis.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RedisSessionCache implements SessionCache {
    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public Session createASession(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    public Session getSession(String id) {
        return sessionRepository.findById(id).orElseThrow(() -> new SessionNotFoundException(id));
    }

    @Override
    public List<Session> getAllSessions() {
        return Stream.iterate(sessionRepository.findAll().iterator(), Iterator::hasNext, UnaryOperator.identity()).map(Iterator::next).collect(Collectors.toList());
    }
}
