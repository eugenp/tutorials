package com.baeldung.spring.data.persistence.truncate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TruncateSpringBootApplication.class,
        properties = "spring.jpa.properties.hibernate.globally_quoted_identifiers=false")
class TruncateIntegrationTest {

    @Autowired
    private MyEntityRepository myEntityRepository;

    @Autowired
    private JdbcTemplateRepository jdbcTemplateRepository;

    @Autowired
    private EntityManagerRepository entityManagerRepository;

    @Test
    void givenSomeData_whenRepositoryTruncateDate_thenNoDataLeft() {
        int givenCount = 3;
        givenDummyData(givenCount);

        myEntityRepository.truncateTable();

        assertThat(myEntityRepository.count())
            .isNotEqualTo(givenCount)
            .isZero();
    }

    @Test
    void givenSomeData_whenEntityManagerTruncateDate_thenNoDataLeft() {
        int givenCount = 3;
        givenDummyData(givenCount);

        entityManagerRepository.truncateTable(MyEntity.TABLE_NAME);

        assertThat(myEntityRepository.count())
            .isNotEqualTo(givenCount)
            .isZero();
    }

    @Test
    void givenSomeData_whenJDBCTemplateTruncateDate_thenNoDataLeft() {
        int givenCount = 3;
        givenDummyData(givenCount);

        jdbcTemplateRepository.truncateTable(MyEntity.TABLE_NAME);

        assertThat(myEntityRepository.count())
            .isNotEqualTo(givenCount)
            .isZero();
    }

    private void givenDummyData(int count) {
        List<MyEntity> input = Stream.generate(this::givenMyEntity).limit(count).collect(Collectors.toList());
        myEntityRepository.saveAll(input);
    }

    private MyEntity givenMyEntity() {
        return new MyEntity();
    }

}