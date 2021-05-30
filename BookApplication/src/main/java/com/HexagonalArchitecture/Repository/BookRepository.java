package com.HexagonalArchitecture.Repository;

import com.HexagonalArchitecture.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    Optional<Book> findOneByName(String name);
}
