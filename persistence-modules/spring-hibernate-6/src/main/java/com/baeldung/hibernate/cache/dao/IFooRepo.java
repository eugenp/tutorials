package com.baeldung.hibernate.cache.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.hibernate.cache.model.Foo;

public interface IFooRepo extends JpaRepository<Foo, Long> {

}
