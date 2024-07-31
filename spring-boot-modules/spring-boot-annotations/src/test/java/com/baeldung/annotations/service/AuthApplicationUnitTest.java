package com.baeldung.annotations.service;

import com.baeldung.annotations.service.abstracts.AbstractAuthenticationService;
import com.baeldung.annotations.service.config.AbstractsAnnotatedTestConfiguration;
import com.baeldung.annotations.service.config.ConcreteClassesAnnotatedTestConfiguration;
import com.baeldung.annotations.service.config.InterfacesAnnotatedTestConfiguration;
import com.baeldung.annotations.service.interfaces.AuthenticationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

public class AuthApplicationUnitTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

    @Test
    void whenOnlyInterfacesAnnotated_noSuchBeanDefinitionExceptionThrown() {
        contextRunner
          .withUserConfiguration(InterfacesAnnotatedTestConfiguration.class)
          .run(context -> {
              Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> {
                  context.getBean(AuthenticationService.class);
              });
          });
    }

    @Test
    void whenOnlyAbstractClassesAnnotated_noSuchBeanDefinitionExceptionThrown() {
        contextRunner
          .withUserConfiguration(AbstractsAnnotatedTestConfiguration.class)
          .run(context -> {
              Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> {
                  context.getBean(AbstractAuthenticationService.class);
              });
          });
    }

    @Test
    void whenConcreteClassesAnnotated_noExceptionThrown() {
        contextRunner
          .withUserConfiguration(ConcreteClassesAnnotatedTestConfiguration.class)
          .run(context -> {
              AuthenticationService inMemoryAuthService = context.getBean(AuthenticationService.class);
              AbstractAuthenticationService ldapAuthService = context.getBean(AbstractAuthenticationService.class);

              Assertions.assertNotNull(inMemoryAuthService);
              Assertions.assertNotNull(ldapAuthService);
          });
    }
}
