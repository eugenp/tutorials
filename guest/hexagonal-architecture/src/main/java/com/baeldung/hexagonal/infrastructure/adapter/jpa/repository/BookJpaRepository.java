package com.baeldung.hexagonal.infrastructure.adapter.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.hexagonal.infrastructure.adapter.jpa.entity.BookEntity;

@Repository
public interface BookJpaRepository extends JpaRepository<BookEntity, Integer> {
	
	BookEntity findByBookId(Integer bookId);

}
