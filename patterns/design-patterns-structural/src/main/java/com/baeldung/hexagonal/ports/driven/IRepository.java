package com.baeldung.hexagonal.ports.driven;


import java.util.Collection;

public interface IRepository<T, K> {
    T create(K vo);

    Collection<T> list();
}
