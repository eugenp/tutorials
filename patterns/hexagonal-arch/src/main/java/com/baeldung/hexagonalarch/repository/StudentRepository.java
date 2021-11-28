package com.baeldung.hexagonalarch.repository;

import com.baeldung.hexagonalarch.domain.model.Student;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

  Optional<Student> findByStudentId(Long studentId);

}
