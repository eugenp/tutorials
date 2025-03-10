package com.baeldung.spring.data.persistence.json;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = JsonAttributeApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JsonAttributeLiveTest {

    @Inject
    private StudentStrRepository studentStrRepository;

    @Inject
    private StudentRepository studentRepository;

    @Test
    @Order(10)
    void whenSaveAnStudentStrEntityAndFindById_thenTheRecordPresentsInDb() {
        String studentId = "23876371";
        String postCode = "KT6 7BB";

        Address address = new Address(postCode, "London");
        StudentStrEntity studentStrEntity = StudentStrEntity.builder()
                .id(studentId)
                .admitYear("2023")
                .address(address)
                .build();

        StudentStrEntity savedStudentStrEntity = studentStrRepository.save(studentStrEntity);

        Optional<StudentStrEntity> studentEntityOptional = studentStrRepository.findById(studentId);
        assertThat(studentEntityOptional.isPresent()).isTrue();

        studentStrEntity = studentEntityOptional.get();
        assertThat(studentStrEntity.getId()).isEqualTo(studentId);
        assertThat(studentStrEntity.getAddress().getPostCode()).isEqualTo(postCode);
    }

    @Test
    @Order(20)
    void whenSaveAnStudentEntityAndFindById_thenTheRecordPresentsInDb() {
        String studentId = "23876371";
        String postCode = "KT6 7BB";

        Address address = new Address(postCode, "London");
        StudentEntity studentEntity = StudentEntity.builder()
                .id(studentId)
                .admitYear("2023")
                .address(address)
                .build();

        StudentEntity savedStudentEntity = studentRepository.save(studentEntity);

        Optional<StudentEntity> studentEntityOptional = studentRepository.findById(studentId);
        assertThat(studentEntityOptional.isPresent()).isTrue();

        studentEntity = studentEntityOptional.get();
        assertThat(studentEntity.getId()).isEqualTo(studentId);
        assertThat(studentEntity.getAddress().getPostCode()).isEqualTo(postCode);
    }

    @Test
    @Order(50)
    void whenFindByAddressPostCode_thenReturnListIsNotEmpty() {
        String postCode = "KT6 7BB";
        List<StudentStrEntity> studentStrEntityList = studentStrRepository.findByAddressPostCode(postCode);
        assertThat(studentStrEntityList).isNotEmpty();
    }

}