package com.baeldung.hexagon.architecture.domain.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.hexagon.architecture.domain.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

}