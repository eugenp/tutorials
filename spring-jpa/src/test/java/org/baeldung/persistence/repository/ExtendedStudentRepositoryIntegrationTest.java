package org.baeldung.persistence.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.annotation.Resource;

import org.baeldung.config.StudentJPAH2Config;
import org.baeldung.persistence.dao.ExtendedStudentRepository;
import org.baeldung.persistence.model.Student;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { StudentJPAH2Config.class})
public class ExtendedStudentRepositoryIntegrationTest {
    @Resource
    private ExtendedStudentRepository extendedStudentRepository;
    
    @Before
    public void setup(){
        Student student = new Student(1, "john");
        extendedStudentRepository.save(student);
        Student student2 = new Student(2, "johnson");
        extendedStudentRepository.save(student2);
        Student student3 = new Student(3, "tom");
        extendedStudentRepository.save(student3);
    }
    
    @Test
    public void givenStudents_whenFindByName_thenGetOk(){
        List<Student> students = extendedStudentRepository.findByAttributeContains("name", "john");
        assertEquals("size incorrect", 2, students.size());        
    }
}
