package com.baeldung.distinct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolRepository extends JpaRepository<School, Integer> {

    List<School> findDistinctByStudentsBirthYear(int birthYear);

    Long countDistinctByStudentsBirthYear(int birthYear);

    @Query("SELECT DISTINCT sch.name FROM School sch JOIN sch.students stu WHERE stu.birthYear = :birthYear")
    List<String> findDistinctSchoolNameByStudentsBirthYear(@Param("birthYear") int birthYear);

    List<NameView> findDistinctNameByStudentsBirthYear(int birthYear);

}
