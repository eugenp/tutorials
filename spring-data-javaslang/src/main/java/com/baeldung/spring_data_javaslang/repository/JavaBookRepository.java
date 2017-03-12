package com.baeldung.spring_data_javaslang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.spring_data_javaslang.model.JavaBook;

import java.util.List;

@Repository
public interface JavaBookRepository extends JpaRepository<JavaBook,Long>{
    JavaBook save(JavaBook book);
        
    JavaBook findById(Long id);
        
    List<JavaBook> findByTitleContaining(String title);
}