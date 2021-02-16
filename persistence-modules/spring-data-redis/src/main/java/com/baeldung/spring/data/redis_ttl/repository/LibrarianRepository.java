package com.baeldung.spring.data.redis_ttl.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.baeldung.spring.data.redis_ttl.entity.Librarian;

@Repository
public interface LibrarianRepository extends CrudRepository<Librarian, Long>{
}