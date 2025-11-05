package com.baeldung.distinct;

import jakarta.inject.Inject;

import java.util.List;

import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = DistinctDataApplication.class, properties = {
        "spring.jpa.show-sql=true",
        "spring.jpa.properties.hibernate.format_sql=true",
        "spring.jpa.generate-ddl=true",
        "spring.jpa.defer-datasource-initialization=true",
        "spring.sql.init.data-locations=classpath:school-data.sql"
})
class DistinctDataIntegrationTest {

    @Inject
    private SchoolRepository schoolRepository;

    @Test
    void whenFindDistinctByStudentsBirthYear_thenReturnOneSchoolEntity() {
        List<School> schoolList = schoolRepository.findDistinctByStudentsBirthYear(2011);
        assertThat(schoolList.size()).isEqualTo(1);
        assertThat(schoolList.get(0).getId()).isEqualTo(2);
    }

    @Test
    void whenCountDistinctByStudentsBirthYear_thenReturnOne() {
        assertThat(schoolRepository.countDistinctByStudentsBirthYear(2011)).isEqualTo(1);
    }

    @Test
    void whenFindDistinctSchoolNameByStudentsBirthYear_thenReturnOneSchoolName() {
        List<String> schoolNameList = schoolRepository.findDistinctSchoolNameByStudentsBirthYear(2011);
        assertThat(schoolNameList.size()).isEqualTo(1);
        assertThat(schoolNameList.get(0)).isEqualTo("St Joesph's College");
    }

    @Test
    void whenFindDistinctNameByStudentsBirthYear_thenReturnOneSchoolName() {
        List<NameView> schoolNameList = schoolRepository.findDistinctNameByStudentsBirthYear(2011);
        assertThat(schoolNameList.size()).isEqualTo(1);
        assertThat(schoolNameList.get(0).getName()).isEqualTo("St Joesph's College");
    }

}