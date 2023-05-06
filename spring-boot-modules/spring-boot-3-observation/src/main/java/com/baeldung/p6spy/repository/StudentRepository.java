package com.baeldung.p6spy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Stream<Student> findAllByFirstName(String firstName);
}