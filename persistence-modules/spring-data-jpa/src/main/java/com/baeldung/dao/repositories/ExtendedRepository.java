package com.baeldung.dao.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ExtendedRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    List<T> findByAttributeContainsText(String attributeName, String text);

}
