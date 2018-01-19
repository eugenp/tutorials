package org.baeldung.inmemory.persistence.dao;

import org.baeldung.inmemory.persistence.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
