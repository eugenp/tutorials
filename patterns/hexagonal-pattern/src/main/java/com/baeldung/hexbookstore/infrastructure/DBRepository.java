package com.baeldung.hexbookstore.infrastructure;

import com.baeldung.hexbookstore.core.BookStore;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DBRepository extends CrudRepository<BookStore, Long> {
}
