package com.baeldung.hexagonal.service;

import com.baeldung.hexagonal.audit.Auditor;
import com.baeldung.hexagonal.events.EventPublisher;
import com.baeldung.hexagonal.model.Item;
import com.baeldung.hexagonal.persistence.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    EventPublisher eventPublisher;

    @Autowired
    Auditor auditor;

    public boolean borrow(String id, String user){
        Item retrieved = itemRepository.retrieve(id);
        if (retrieved != null) {
            eventPublisher.publish(retrieved);
            auditor.audit(retrieved);
        }
        return retrieved != null;
    }
}
