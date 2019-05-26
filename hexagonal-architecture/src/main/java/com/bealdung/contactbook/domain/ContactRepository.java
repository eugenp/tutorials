package com.bealdung.contactbook.domain;

import java.util.Collection;

public interface ContactRepository {
    void save(Contact contact);
    Collection<Contact> findByName(String name);
}
