package com.bealdung.contactbook.domain;

import java.util.Collection;

public interface ContactSearcher {
    Collection<Contact> findByName(String name);
}
