package com.baeldung.hibernate.cache.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.hibernate.cache.model.Bar;

public interface IBarRepo extends JpaRepository<Bar, Long> {

}
