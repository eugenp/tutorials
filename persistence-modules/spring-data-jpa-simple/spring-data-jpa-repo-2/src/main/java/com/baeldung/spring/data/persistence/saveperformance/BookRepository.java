package com.baeldung.spring.data.persistence.saveperformance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
