package com.baeldung.hexagonal.app.repository;

import com.baeldung.hexagonal.app.model.Contact;

public interface ContactRepository {

    public void save(Contact contact);
}
