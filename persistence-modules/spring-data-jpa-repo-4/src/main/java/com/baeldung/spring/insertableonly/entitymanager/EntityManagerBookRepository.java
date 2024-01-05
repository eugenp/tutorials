package com.baeldung.spring.insertableonly.entitymanager;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityManagerBookRepository extends JpaRepository<EntityManagerBook, Long>,
  PersistableEntityManagerBookRepository<EntityManagerBook> {
}
