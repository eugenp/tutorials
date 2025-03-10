package com.baeldung.spring.notamanagedtype;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.boot.SpringApplication.run;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.baeldung.spring.notamanagedtype.jakartaannotation.EntityWithJakartaAnnotationApplication;
import com.baeldung.spring.notamanagedtype.missedannotation.EntityWithoutAnnotationApplication;
import com.baeldung.spring.notamanagedtype.missedannotationfixed.EntityWithoutAnnotationFixedApplication;
import com.baeldung.spring.notamanagedtype.missedannotationfixed.EntityWithoutAnnotationFixedRepository;
import com.baeldung.spring.notamanagedtype.missedentityscan.app.WrongEntityScanApplication;
import com.baeldung.spring.notamanagedtype.missedentityscan.fixed.app.WrongEntityScanFixedApplication;
import com.baeldung.spring.notamanagedtype.missedentityscan.repository.CorrectEntityRepository;

class NotManagedTypeExceptionIntegrationTest {
    @Test
    void givenEntityWithoutAnnotationApplication_whenBootstrap_thenExpectedExceptionThrown() {
        Exception exception = assertThrows(Exception.class,
          () -> run(EntityWithoutAnnotationApplication.class));

        assertThat(exception)
          .getRootCause()
          .hasMessageContaining("Not a managed type");
    }

    @Test
    void givenEntityWithoutAnnotationApplicationFixed_whenBootstrap_thenRepositoryBeanShouldBePresentInContext() {
        ConfigurableApplicationContext context = run(EntityWithoutAnnotationFixedApplication.class);
        EntityWithoutAnnotationFixedRepository repository = context
          .getBean(EntityWithoutAnnotationFixedRepository.class);

        assertThat(repository).isNotNull();
    }

    @Disabled
    @Test
    void givenEntityWithJakartaAnnotationApplication_whenBootstrap_thenExpectedExceptionThrown() {
        Exception exception = assertThrows(Exception.class,
          () -> run(EntityWithJakartaAnnotationApplication.class));

        assertThat(exception)
          .getRootCause()
          .hasMessageContaining("Not a managed type");
    }

    @Test
    void givenWrongEntityScanApplication_whenBootstrap_thenExpectedExceptionThrown() {
        Exception exception = assertThrows(Exception.class,
          () -> run(WrongEntityScanApplication.class));

        assertThat(exception)
          .getRootCause()
          .hasMessageContaining("Not a managed type");
    }

    @Test
    void givenWrongEntityScanApplicationFixed_whenBootstrap_thenRepositoryBeanShouldBePresentInContext() {
        SpringApplication app = new SpringApplication(WrongEntityScanFixedApplication.class);
        app.setAdditionalProfiles("test");
        ConfigurableApplicationContext context = app.run();
        CorrectEntityRepository repository = context
          .getBean(CorrectEntityRepository.class);

        assertThat(repository).isNotNull();
    }
}
