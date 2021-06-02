package com.HexagonalArchitecture.BookApplication.Repository;

import com.HexagonalArchitecture.BookApplication.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findOneByName(String name);
}
