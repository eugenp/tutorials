package com.baeldung.sample.pets.boundary;

import com.baeldung.sample.pets.domain.PetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
public class PetDtoMapperIntegrationTest {

    @Configuration
    @ComponentScan(basePackageClasses = PetDtoMapper.class)
    static class PetDtoMapperTestConfig {

        /*
         * This would be necessary because the controller is also initialized
         * and needs the service, although we do not want to test it here.
         *
         * Solutions:
         *  - place the mapper into a separate sub package
         *  - do not test the mapper separately, test it integrated within the controller
         *    (recommended)
         */
        @Bean
        PetService createServiceMock() {
            return mock(PetService.class, MockReset.withSettings(MockReset.AFTER));
        }

    }

    @Autowired
    PetDtoMapper mapper;

    @Test
    void shouldExist() { // simply test correct test setup
        assertThat(mapper).isNotNull();
    }

}
