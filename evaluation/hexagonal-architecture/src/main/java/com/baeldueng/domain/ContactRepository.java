package com.baeldueng.domain;

public interface ContactRepository {
    Contact save(Contact contact);

    Contact getById(String id);
}
