package com.baeldung.paging;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentNamedEntityGraphRepository extends JpaRepository<Student, String> {

    @EntityGraph(value = "Student.school")
    Page<Student> findAll(Pageable pageable);

}