package com.baeldung.spring_data.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.spring_data.model.JavaBook;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.concurrent.Future;

import javax.transaction.Transactional;

@Repository
public interface Java8BookRepository extends JpaRepository<JavaBook,Long>{
    JavaBook save(JavaBook book);
    
    JavaBook findById(Long id);
    
    Future<JavaBook> findLongRunningById(Long Id);
        
    Optional<Stream<JavaBook>> findAllByTitleIsNotNull();
}