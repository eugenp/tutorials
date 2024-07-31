package com.baeldung.spring.data.persistence.json;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<StudentEntity, String> {

    @Query(value = "SELECT * FROM student WHERE address->>'postCode' = :postCode", nativeQuery = true)
    List<StudentEntity> findByAddressPostCode(@Param("postCode") String postCode);

}