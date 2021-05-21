package com.baeldung.annotations.conditional;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class IsDevEnvConditionUnitTest {

    @Test
    public void whenDevEnvEnabled_thenDevEnvConditionShouldPass() {
        System.setProperty("env", "dev");

        Assertions.assertTrue(
          new IsDevEnvCondition().matches(
            Mockito.mock(ConditionContext.class), Mockito.mock(AnnotatedTypeMetadata.class)
          )
        );
    }

    @Test
    public void whenDevEnvNotEnabled_thenDevEnvConditionShouldNotPass() {
        Assertions.assertTrue(
          new IsDevEnvCondition().matches(
            Mockito.mock(ConditionContext.class), Mockito.mock(AnnotatedTypeMetadata.class)
          )
        );
    }

}
