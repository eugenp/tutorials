package com.baeldung.spring.data.jpa.jsontypeexception;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository  extends JpaRepository<Student, Long> {

    @Query(value = "INSERT INTO student (admit_year, address) VALUES (:admitYear, :address) RETURNING *", nativeQuery = true)
    Student insertJsonData(@Param("admitYear") String admitYear, @Param("address") String address);
}
