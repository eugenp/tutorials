package org.baeldung.persistence.repository;

import org.baeldung.config.StudentJpaConfig;
import org.baeldung.inmemory.persistence.dao.ManyStudentRepository;
import org.baeldung.inmemory.persistence.dao.ManyTagRepository;
import org.baeldung.inmemory.persistence.dao.StudentRepository;
import org.baeldung.inmemory.persistence.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { StudentJpaConfig.class }, loader = AnnotationConfigContextLoader.class)
@Transactional
public class InMemoryDBIntegrationTest {
    
    @Resource
    private StudentRepository studentRepository;

    @Resource
    private ManyStudentRepository manyStudentRepository;

    @Resource
    private ManyTagRepository manyTagRepository;
    
    private static final long ID = 1;
    private static final String NAME="john";
    
    @Test
    public void givenStudent_whenSave_thenGetOk(){
        Student student = new Student(ID, NAME);
        studentRepository.save(student);
        
        Student student2 = studentRepository.findOne(ID);
        assertEquals("name incorrect", NAME, student2.getName());        
    }

    @Test
    public void givenStudentWithTags_whenSave_thenGetByTagOk(){
        Student student = new Student(ID, NAME);
        student.setTags(Arrays.asList("full time", "computer science"));
        studentRepository.save(student);

        Student student2 = studentRepository.retrieveByTag("full time").get(0);
        assertEquals("name incorrect", NAME, student2.getName());
    }

    @Test
    public void givenMultipleStudentsWithTags_whenSave_thenGetByTagReturnsCorrectCount(){
        Student student = new Student(0, "Larry");
        student.setTags(Arrays.asList("full time", "computer science"));
        studentRepository.save(student);

        Student student2 = new Student(1, "Curly");
        student2.setTags(Arrays.asList("part time", "rocket science"));
        studentRepository.save(student2);

        Student student3 = new Student(2, "Moe");
        student3.setTags(Arrays.asList("full time", "philosophy"));
        studentRepository.save(student3);

        Student student4 = new Student(3, "Shemp");
        student4.setTags(Arrays.asList("part time", "mathematics"));
        studentRepository.save(student4);

        List<Student> students = studentRepository.retrieveByTag("full time");
        assertEquals("size incorrect", 2, students.size());
    }

    @Test
    public void givenStudentWithTags_whenSave_thenGetByNameAndTagOk(){
        Student student = new Student(ID, NAME);
        student.setTags(Arrays.asList("full time", "computer science"));
        studentRepository.save(student);

        Student student2 = studentRepository.retrieveByNameFilterByTag("John", "full time").get(0);
        assertEquals("name incorrect", NAME, student2.getName());
    }

    @Test
    public void givenStudenWithSkillTags_whenSave_thenGetByNameAndSkillTag() {
        Student student = new Student(1, "Will");
        SkillTag skill1 = new SkillTag("java", 5);
        student.setSkillTags(Arrays.asList(skill1));
        studentRepository.save(student);

        Student student2 = new Student(2, "Joe");
        SkillTag skill2 = new SkillTag("java", 1);
        student2.setSkillTags(Arrays.asList(skill2));
        studentRepository.save(student2);

        List<Student> students = studentRepository.retrieveByNameFilterByMinimumSkillTag("java", 3);
        assertEquals("size incorrect", 1, students.size());
    }

    @Test
    public void givenStudentWithKVTags_whenSave_thenGetByTagOk(){
        Student student = new Student(0, "John");
        student.setKVTags(Arrays.asList(new KVTag("department", "computer science")));
        studentRepository.save(student);

        Student student2 = new Student(1, "James");
        student2.setKVTags(Arrays.asList(new KVTag("department", "humanities")));
        studentRepository.save(student2);

        List<Student> students = studentRepository.retrieveByKeyTag("department");
        assertEquals("size incorrect", 2, students.size());
    }

    @Test
    public void givenStudentWithManyTags_whenSave_theyGetByTagOk() {
        ManyTag tag = new ManyTag("full time");
        manyTagRepository.save(tag);

        ManyStudent student = new ManyStudent("John");
        student.setManyTags(Collections.singleton(tag));
        manyStudentRepository.save(student);

        List<ManyStudent> students = manyStudentRepository.findByManyTags_Name("full time");
        assertEquals("size incorrect", 1, students.size());
    }

}
