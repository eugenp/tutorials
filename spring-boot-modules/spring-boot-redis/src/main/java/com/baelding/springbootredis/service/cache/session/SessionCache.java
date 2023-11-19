package com.baelding.springbootredis.service.cache.session;

import com.baelding.springbootredis.model.Session;

import java.util.List;

public interface SessionCache {
    Session createASession(Session session);
    Session getSession(String id);
    List<Session> getAllSessions();

}
