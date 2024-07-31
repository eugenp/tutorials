package com.baeldung.sample.test.slices;

import com.baeldung.sample.pets.boundary.PetsBoundaryLayer;
import com.baeldung.sample.pets.boundary.PetsController;
import com.baeldung.sample.pets.domain.PetService;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.mockito.Mockito.mock;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@WebMvcTest(controllers = PetsController.class)
@ComponentScan(basePackageClasses = PetsBoundaryLayer.class)
@Import(PetsBoundaryTest.PetBoundaryTestConfiguration.class)
// further features that can help to configure and execute tests
@ActiveProfiles({ "test", "boundary-test" })
@Tag("integration-test")
@Tag("boundary-test")
public @interface PetsBoundaryTest {

    @TestConfiguration
    class PetBoundaryTestConfiguration {

        @Primary
        @Bean
        PetService createPetServiceMock() {
            return mock(
              PetService.class,
              MockReset.withSettings(MockReset.AFTER)
            );
        }

    }

}
