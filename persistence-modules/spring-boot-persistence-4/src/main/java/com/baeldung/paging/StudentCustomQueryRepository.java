package com.baeldung.paging;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentCustomQueryRepository extends JpaRepository<Student, String> {

    @Query(value = "SELECT stu FROM Student stu LEFT JOIN FETCH stu.school ",
            countQuery = "SELECT COUNT(stu) FROM Student stu")
    Page<Student> findAll(Pageable pageable);

}
