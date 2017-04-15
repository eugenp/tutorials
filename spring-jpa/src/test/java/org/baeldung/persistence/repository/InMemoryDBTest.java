package org.baeldung.persistence.repository;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import org.baeldung.config.StudentJpaConfig;
import org.baeldung.persistence.model.Student;
import org.baeldung.persistence.dao.StudentRepository;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { StudentJpaConfig.class }, loader = AnnotationConfigContextLoader.class)
@Transactional
public class InMemoryDBTest {
    
    @Resource
    private StudentRepository studentRepository;
    
    private static final long ID = 1;
    private static final String NAME="john";
    
    @Test
    public void givenStudent_whenSave_thenGetOk(){
        Student student = new Student(ID, NAME);
        studentRepository.save(student);
        
        Student student2 = studentRepository.findOne(ID);
        assertEquals("name incorrect", NAME, student2.getName());        
    }

}
