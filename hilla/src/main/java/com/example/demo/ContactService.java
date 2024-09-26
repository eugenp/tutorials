package com.example.demo;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import com.vaadin.hilla.crud.CrudRepositoryService;

import java.util.List;

@BrowserCallable
@AnonymousAllowed
public class ContactService extends CrudRepositoryService<Contact, Long, ContactRepository> {

    public List<Contact> findAll() {
        return getRepository().findAll();
    }

    public Contact findById(Long id) {
        return getRepository().findById(id)
            .orElseThrow();
    }
}
