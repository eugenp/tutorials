package com.baeldung.annotations.conditional;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class Java8ConditionUnitTest {

    @Test
    public void whenOnJava8_thenJava8ConditionShouldPass() {

        try (MockedStatic<ConditionalUtils> theMock = Mockito.mockStatic(ConditionalUtils.class)) {
            theMock.when(ConditionalUtils::isJava8)
              .thenReturn(true);
            Assertions.assertTrue(
              new Java8Condition().matches(
                Mockito.mock(ConditionContext.class), Mockito.mock(AnnotatedTypeMetadata.class)
              )
            );
        }

    }

    @Test
    public void whenNotOnJava8_thenJava8ConditionShouldNotPass() {
        try (MockedStatic<ConditionalUtils> theMock = Mockito.mockStatic(ConditionalUtils.class)) {
            theMock.when(ConditionalUtils::isJava8)
              .thenReturn(false);
            Assertions.assertFalse(
              new Java8Condition().matches(
                Mockito.mock(ConditionContext.class), Mockito.mock(AnnotatedTypeMetadata.class)
              )
            );
        }
    }

}
