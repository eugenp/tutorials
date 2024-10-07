package com.baeldung.spring.data.jpa.optionalfields.objects;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.baeldung.spring.data.jpa.optionalfields.Book;

@Repository
public interface BookObjectsRepository extends JpaRepository<Book, Integer> {
    @Query("SELECT b.id, b.title, b.author FROM Book b")
    List<Object[]> fetchBooks();
}

