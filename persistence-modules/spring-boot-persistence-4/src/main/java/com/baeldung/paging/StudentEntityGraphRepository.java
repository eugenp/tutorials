package com.baeldung.paging;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentEntityGraphRepository extends JpaRepository<Student, String> {

    @EntityGraph(attributePaths = "school")
    Page<Student> findAll(Pageable pageable);

}