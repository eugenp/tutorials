package com.baeldung.spring.jinq.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CarRepositoryIntegrationTest {

    @Autowired
    private CarRepository repository;

    @Test
    void givenACar_whenFilter_thenShouldBeFound() {
        assertThat(repository.findByModel("model1")
            .isPresent()).isFalse();
    }

    @Test
    void givenACar_whenMultipleFilters_thenShouldBeFound() {
        assertThat(repository.findByModelAndDescription("model1", "desc")
            .isEmpty()).isTrue();
    }

    @Test
    void whenUseASelectClause() {
        assertThat(repository.findWithModelYearAndEngine()
            .isEmpty()).isTrue();
    }

    @Test
    void whenUsingOneToOneRelationship() {
        assertThat(repository.findManufacturerByModel("model1")).isNotNull();
    }
}
