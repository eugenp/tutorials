package com.baeldung.spring.jinq.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.spring.jinq.JinqApplication;

@ContextConfiguration(classes = JinqApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CarRepositoryIntegrationTest {

    @Autowired
    private CarRepository repository;

    @Test
    public void givenACar_whenFilter_thenShouldBeFound() {
        assertThat(repository.findByModel("model1")
            .isPresent()).isFalse();
    }

    @Test
    public void givenACar_whenMultipleFilters_thenShouldBeFound() {
        assertThat(repository.findByModelAndDescription("model1", "desc")
            .isEmpty()).isTrue();
    }

    @Test
    public void whenUseASelectClause() {
        assertThat(repository.findWithModelYearAndEngine()
            .isEmpty()).isTrue();
    }

    @Test
    public void whenUsingOneToOneRelationship() {
        assertThat(repository.findManufacturerByModel("model1")).isNotNull();
    }
}
