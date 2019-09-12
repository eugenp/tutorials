package com.baeldung.hibernate.H2.EntityMapToTwoTables.repository;

import com.baeldung.hibernate.H2.EntityMapToTwoTables.entity.StudentEntity;
import com.spring.boot.twoTableEntity.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {
}
