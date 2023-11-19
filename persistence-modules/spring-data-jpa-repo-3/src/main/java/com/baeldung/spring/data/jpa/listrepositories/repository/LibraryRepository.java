package com.baeldung.spring.data.jpa.listrepositories.repository;

import com.baeldung.spring.data.jpa.listrepositories.entity.Library;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryRepository extends CrudRepository<Library, Long> {
    @Query("SELECT l FROM library l JOIN FETCH l.books WHERE l.id = (:id)")
    Library findById(@Param("id") long id);
}
