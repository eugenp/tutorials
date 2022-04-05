package com.baeldung.inmemory.persistence.dao;

import com.baeldung.inmemory.persistence.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s JOIN s.tags t WHERE t = LOWER(:tag)")
    List<Student> retrieveByTag(@Param("tag") String tag);

    @Query("SELECT s FROM Student s JOIN s.tags t WHERE s.name = LOWER(:name) AND t = LOWER(:tag)")
    List<Student> retrieveByNameFilterByTag(@Param("name") String name, @Param("tag") String tag);

    @Query("SELECT s FROM Student s JOIN s.skillTags t WHERE t.name = LOWER(:tagName) AND t.value > :tagValue")
    List<Student> retrieveByNameFilterByMinimumSkillTag(@Param("tagName") String tagName, @Param("tagValue") int tagValue);

    @Query("SELECT s FROM Student s JOIN s.kvTags t WHERE t.key = LOWER(:key)")
    List<Student> retrieveByKeyTag(@Param("key") String key);

}
