package com.baeldung.hexagonal;

public class Adapter implements RepositoryPort {
    @Override
    public void create(Object request) {
        //TODO: do something
    }

    @Override
    public Object view(int userId) {
        return new Object();
    }
}
