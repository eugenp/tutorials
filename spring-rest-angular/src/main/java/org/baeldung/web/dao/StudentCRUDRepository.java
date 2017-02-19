package org.baeldung.web.dao;

import java.util.List;

import org.baeldung.web.entity.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "student", path = "student")
public interface StudentCRUDRepository extends CrudRepository<Student, Long> {
    List<Student> findByName(@Param("name") String name);
}
