package com.baeldung.spring.data.jpa.optionalfields.objects;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.baeldung.spring.data.jpa.optionalfields.Book;

@Repository
public interface BookTupleRepository extends JpaRepository<Book, Integer> {
    @Query(value = "SELECT b.id, b.title, b.author FROM Book b", nativeQuery = true)
    List<Tuple> fetchBooks();
}
