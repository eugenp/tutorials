package com.baeldung.eval.hexagonal.business;

public interface PostStorePort {
    void save(Post post) throws StoreException;

    Post read(String id) throws StoreException;
}
