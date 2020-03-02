package com.baeldung.spring.data.cassandra.repository;

import com.baeldung.spring.data.cassandra.model.Book;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CassandraRepository<Book> {

    @Query("select * from book where title = ?0 and publisher=?1")
    Iterable<Book> findByTitleAndPublisher(String title, String publisher);

}
