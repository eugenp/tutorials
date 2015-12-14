package com.baeldung.dao;

import java.util.List;

import com.baeldung.model.Foo;

public interface FooDao {

    public List<Foo> listFoos();

    public void insertFoos(String user, String name, String description);

    public List<Foo> getFoos(String userId);

    public void remove(long id);

}
