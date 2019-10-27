package org.baeldung.adapter.secondary;

import org.baeldung.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepositoryAdapter extends JpaRepository<Student, Long> {
}
