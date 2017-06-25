package com.baeldung.spring_data.repository;

import com.baeldung.spring_data.model.JavaBook;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JavaBookRepository extends JpaRepository<JavaBook,Long>{
    JavaBook save(JavaBook book);
        
    JavaBook findById(Long id);
        
    List<JavaBook> findByTitleContaining(String title);
}