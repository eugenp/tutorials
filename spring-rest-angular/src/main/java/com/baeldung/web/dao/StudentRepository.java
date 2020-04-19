package com.baeldung.web.dao;

import com.baeldung.web.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> 
{

}
