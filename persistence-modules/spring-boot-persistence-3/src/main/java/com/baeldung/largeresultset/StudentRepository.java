package com.baeldung.largeresultset;

import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Slice<Student> findAllByFirstName(String firstName, Pageable page);
    Page<Student> findAllByLastName(String firstName, Pageable page);
    Stream<Student> findAllByFirstName(String firstName);
}