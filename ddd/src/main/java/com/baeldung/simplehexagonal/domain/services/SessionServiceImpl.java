package com.baeldung.simplehexagonal.domain.services;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.baeldung.simplehexagonal.domain.Session;
import com.baeldung.simplehexagonal.domain.repository.SessionRepository;

public class SessionServiceImpl implements SessionService {

    private SessionRepository sessionRepository;

    public SessionServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public List<Session> findAll() {
        return sessionRepository.findAll();
    }

    public Session get(Long id) {
        return sessionRepository.findById(id);
    }

    public Session create(Session session) {
        return sessionRepository.save(session);
    }

    public void delete(Long id) {
        sessionRepository.deleteById(id);
    }

    public Session update(Long id, Session session) {
        Session currentSession = sessionRepository.findById(id);
        BeanUtils.copyProperties(session, currentSession, "session_id");
        return sessionRepository.save(currentSession);
    }

}
