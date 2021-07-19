package com.baeldung.springdatageode.service;

import org.apache.geode.cache.query.CqEvent;
import org.springframework.data.gemfire.listener.annotation.ContinuousQuery;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    
    @ContinuousQuery(query = "SELECT * FROM /Authors a WHERE a.id = 1")
    public void process(CqEvent event) {
        System.out.println("Author #" + event.getKey() + " updated to " + event.getNewValue());
    }
}
