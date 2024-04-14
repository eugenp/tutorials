package com.baeldung.spring.notamanagedtypeexceptioninspringdatajpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.boot.SpringApplication.run;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;

import com.baeldung.spring.notamanagedtypeexceptioninspringdatajpa.entitywithoutannotation.EntityWithoutAnnotationApplication;
import com.baeldung.spring.notamanagedtypeexceptioninspringdatajpa.entitywithoutannotationfixed.EntityWithoutAnnotationFixedApplication;
import com.baeldung.spring.notamanagedtypeexceptioninspringdatajpa.entitywithoutannotationfixed.EntityWithoutAnnotationFixedRepository;
import com.baeldung.spring.notamanagedtypeexceptioninspringdatajpa.entitywithjakartaannotation.EntityWithJakartaAnnotationApplication;
import com.baeldung.spring.notamanagedtypeexceptioninspringdatajpa.wrongentityscanapplication.app.WrongEntityScanApplication;
import com.baeldung.spring.notamanagedtypeexceptioninspringdatajpa.wrongentityscanapplication.fixed.app.WrongEntityScanFixedApplication;
import com.baeldung.spring.notamanagedtypeexceptioninspringdatajpa.wrongentityscanapplication.repository.CorrectEntityRepository;

class NotManagedTypeExceptionIntegrationTest {
    @Test
    void givenEntityWithoutAnnotationApplicationWhenBootstrapThenExpectedExceptionThrown() {
        Exception exception = assertThrows(Exception.class,
          () -> run(EntityWithoutAnnotationApplication.class));

        assertThat(exception)
          .getRootCause()
          .hasMessageContaining("Not a managed type");
    }

    @Test
    void givenEntityWithoutAnnotationApplicationFixedWhenBootstrapThenRepositoryBeanShouldBePresentInContext() {
        ConfigurableApplicationContext context = run(EntityWithoutAnnotationFixedApplication.class);
        EntityWithoutAnnotationFixedRepository repository = context
          .getBean(EntityWithoutAnnotationFixedRepository.class);

        assertThat(repository).isNotNull();
    }

    @Test
    void givenEntityWithJakartaAnnotationApplicationWhenBootstrapThenExpectedExceptionThrown() {
        Exception exception = assertThrows(Exception.class,
          () -> run(EntityWithJakartaAnnotationApplication.class));

        assertThat(exception)
          .getRootCause()
          .hasMessageContaining("Not a managed type");
    }

    @Test
    void givenWrongEntityScanApplicationWhenBootstrapThenExpectedExceptionThrown() {
        Exception exception = assertThrows(Exception.class,
          () -> run(WrongEntityScanApplication.class));

        assertThat(exception)
          .getRootCause()
          .hasMessageContaining("Not a managed type");
    }

    @Test
    void givenWrongEntityScanApplicationFixedWhenBootstrapThenRepositoryBeanShouldBePresentInContext() {
        ConfigurableApplicationContext context = run(WrongEntityScanFixedApplication.class);
        CorrectEntityRepository repository = context
          .getBean(CorrectEntityRepository.class);

        assertThat(repository).isNotNull();
    }
}
