package com.baeldung.dynamicquery.repository;

import com.baeldung.dynamicquery.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface StudentRepository extends JpaRepository<Student, Long>, QuerydslPredicateExecutor<Student>, JpaSpecificationExecutor<Student> {
}
