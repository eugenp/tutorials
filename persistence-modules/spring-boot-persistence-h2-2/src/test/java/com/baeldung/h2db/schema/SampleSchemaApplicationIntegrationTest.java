package com.baeldung.h2db.schema;

import com.baeldung.h2db.schema.entity.Student;
import com.baeldung.h2db.schema.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SampleSchemaApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("schema-sample")
@Transactional
class SampleSchemaApplicationIntegrationTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void whenSaveStudent_thenThrowsException() {
        Student student = Student.builder()
                .studentId("24567433")
                .name("David Lloyds")
                .build();

        assertThatThrownBy(() -> studentRepository.save(student))
                .isInstanceOf(InvalidDataAccessResourceUsageException.class);
    }

}