package com.baeldung.spring.data.jpa.jsontypeexception;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentWithTypeAnnotationRepository extends JpaRepository<StudentWithTypeAnnotation, Long> {

    @Query(value = "INSERT INTO student_json (admit_year, address) VALUES (:admitYear, CAST(:address AS JSONB)) RETURNING *", nativeQuery = true)
    StudentWithTypeAnnotation insertJsonData(@Param("admitYear") String admitYear, @Param("address") String address);
}
