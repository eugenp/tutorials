package com.baeldung.hibernate.dao;

import java.util.List;

public interface GenericDao<T> {
    
    void save (T entity);
    void delete (T Entity);
    T findByName(String name);
    List<T> findAll();
    void populate();
}
