package org.baeldung.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import org.baeldung.persistence.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
