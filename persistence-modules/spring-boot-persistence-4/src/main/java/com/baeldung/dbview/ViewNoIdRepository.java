package com.baeldung.dbview;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;

@NoRepositoryBean
public interface ViewNoIdRepository<T, K> extends Repository<T, K> {

    long count();

    List<T> findAll();

}