package com.baeldung.h2db.schema;

import com.baeldung.h2db.schema.entity.Student;
import com.baeldung.h2db.schema.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = InitScriptCreateSchemaApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("schema-script")
@Transactional
class InitScriptCreateSchemaApplicationIntegrationTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void whenSaveStudent_thenStudentIsSaved() {
        long beforeCount = studentRepository.count();

        Student student = Student.builder()
                .studentId("24567433")
                .name("David Lloyds")
                .build();
        student = studentRepository.save(student);

        assertThat(student).isNotNull();
        assertThat(studentRepository.count() - beforeCount).isEqualTo(1);
    }

}