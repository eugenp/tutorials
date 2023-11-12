package com.baeldung.persistence.repository;

import com.baeldung.config.StudentJpaConfig;
import com.baeldung.inmemory.persistence.dao.ManyStudentRepository;
import com.baeldung.inmemory.persistence.dao.ManyTagRepository;
import com.baeldung.inmemory.persistence.dao.StudentRepository;
import com.baeldung.inmemory.persistence.model.ManyStudent;
import com.baeldung.inmemory.persistence.model.ManyTag;
import com.baeldung.inmemory.persistence.model.SkillTag;
import com.baeldung.inmemory.persistence.model.Student;
import com.baeldung.inmemory.persistence.model.KVTag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
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
@DirtiesContext
public class AdvancedTaggingIntegrationTest {
    @Resource
    private StudentRepository studentRepository;

    @Resource
    private ManyStudentRepository manyStudentRepository;

    @Resource
    private ManyTagRepository manyTagRepository;

    @Test
    public void givenStudentWithSkillTags_whenSave_thenGetByNameAndSkillTag() {
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
    public void givenStudentWithKVTags_whenSave_thenGetByTagOk() {
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
