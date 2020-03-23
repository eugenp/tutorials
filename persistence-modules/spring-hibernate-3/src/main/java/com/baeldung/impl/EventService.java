package com.baeldung.port.inbound;


import com.baeldung.port.model.Event;
import com.baeldung.port.outbound.IEventDao;
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
