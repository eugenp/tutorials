package com.baeldung.hexagonalarchitecture.repositories;

import com.baeldung.hexagonalarchitecture.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {


}
