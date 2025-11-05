package com.baeldung.paging;

import com.baeldung.listvsset.util.TestConfig;
import io.hypersistence.utils.jdbc.validator.SQLStatementCountValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

import static io.hypersistence.utils.jdbc.validator.SQLStatementCountValidator.assertSelectCount;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest(classes = {PagingApplication.class, TestConfig.class}, properties = {
        "spring.jpa.show-sql=false",
        "spring.jpa.properties.hibernate.format_sql=false",
        "spring.jpa.generate-ddl=true",
        "spring.jpa.defer-datasource-initialization=true",
        "spring.sql.init.data-locations=classpath:school-student-data.sql"
})
@Transactional
class StudentRepositoryIntegrationTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentCustomQueryRepository studentCustomQueryRepository;

    @Autowired
    private StudentEntityGraphRepository studentEntityGraphRepository;

    @Autowired
    private StudentNamedEntityGraphRepository studentNamedEntityGraphRepository;

    private static final ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setUp() {
        SQLStatementCountValidator.reset();
    }

    @Test
    public void whenGetStudentsWithPageRequestOfTwo_thenReturnTwoRows() {
        int rows = 2;
        Pageable pageable = PageRequest.of(0, rows);
        Page<Student> studentPage = studentRepository.findAll(pageable);

        // Then
        List<Student> studentList = studentPage.getContent();
        assertThat(studentList.size()).isEqualTo(rows);
    }

    @Test
    public void whenGetStudentsWithUnpagedAndSort_thenReturnAllResultsSorted() {
        Sort sort = Sort.by(Sort.Direction.ASC, "lastName");
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE).withSort(sort);
        Page<Student> studentPage = studentRepository.findAll(pageable);

        // Then
        List<Student> studentList = studentPage.getContent();
        assertThat(studentList.size()).isEqualTo(studentPage.getTotalElements());
        assertThat(studentList).isSortedAccordingTo(Comparator.comparing(Student::getLastName));
    }


    @Test
    public void whenGetStudentsWithSchool_thenMultipleSelectQueriesAreExecuted() {
        Page<Student> studentPage = studentRepository.findAll(Pageable.unpaged());
        studentPage.get().map(student -> modelMapper.map(student, StudentWithSchoolNameDTO.class)).toList();
        assertSelectCount(studentPage.getContent().size() + 1);
    }

    @Test
    public void whenGetStudentsByCustomQuery_thenOneSelectQueryIsExecuted() {
        Page<Student> studentPage = studentCustomQueryRepository.findAll(Pageable.unpaged());
        studentPage.get().map(student -> modelMapper.map(student, StudentWithSchoolNameDTO.class)).toList();
        assertSelectCount(1);
    }

    @Test
    public void whenGetStudentsByEntityGraph_thenOneSelectQueryIsExecuted() {
        Page<Student> studentPage = studentEntityGraphRepository.findAll(Pageable.unpaged());
        studentPage.get().map(student -> modelMapper.map(student, StudentWithSchoolNameDTO.class)).toList();
        assertSelectCount(1);
    }

    @Test
    public void whenGetStudentsByNamedEntityGraph_thenOneSelectQueryIsExecuted() {
        Page<Student> studentPage = studentNamedEntityGraphRepository.findAll(Pageable.unpaged());
        studentPage.get().map(student -> modelMapper.map(student, StudentWithSchoolNameDTO.class)).toList();
        assertSelectCount(1);
    }

}