package com.baeldung.simplehexagonal.domain.services;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.util.Assert.notNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.baeldung.simplehexagonal.domain.Session;
import com.baeldung.simplehexagonal.domain.repository.SessionRepository;
import com.baeldung.simplehexagonal.domain.services.SessionServiceImpl;

public class SessionServiceUnitTest {

    private SessionServiceImpl sessionService;

    private SessionRepository sessionRepository;

    Session session;

    @BeforeEach
    void setUp() {
        sessionRepository = mock(SessionRepository.class);
        sessionService = new SessionServiceImpl(sessionRepository);

        session = new Session();
        session.setSessionId(1L);
        session.setSessionName("Introduction to Hexagonal Architecture");
        session.setSessionDescription("A quick and practical eample of Hexagonal Architecture");
        session.setSessionLength(30);

        when(sessionRepository.save(Mockito.any())).thenReturn(session);
        when(sessionRepository.findById(1L)).thenReturn(session);
    }

    @Test
    void testFindAll() {
        List<Session> list = sessionService.findAll();
        notNull(list, "should not return null");
    }

    @Test
    void testGet() {
        Session mySession = sessionService.get(1L);
        notNull(mySession, "should not return null");
    }

    @Test
    void testCreate() {
        session = sessionService.create(new Session());
        notNull(session.getSessionId(), "Id should be populated");
    }

    @Test
    void testDelete() {
        try {
            sessionService.delete(1L);
        } catch (Exception e) {
            fail("Should not throw error");
        }
    }

    @Test
    void testUpdate() {
        Session updatedSession = sessionService.update(1L, new Session());
        notNull(updatedSession, "Id should be populated");
    }

}
