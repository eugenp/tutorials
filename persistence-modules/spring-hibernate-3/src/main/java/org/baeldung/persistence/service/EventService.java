package org.baeldung.persistence.service;


import org.baeldung.persistence.dao.IEventDao;
import org.baeldung.persistence.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EventService {

    @Autowired
    private IEventDao dao;

    public EventService() {
        super();
    }

    // API

    public void create(final Event entity) {
        dao.create(entity);
    }

}
