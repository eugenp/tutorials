package com.baeldung.hibernate.cache.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.hibernate.cache.model.Roo;

public interface IRooRepo extends JpaRepository<Roo, Long> {

}
