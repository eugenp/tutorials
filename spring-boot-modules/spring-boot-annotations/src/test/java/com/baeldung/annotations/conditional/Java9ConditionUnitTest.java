package com.baeldung.annotations.conditional;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class Java9ConditionUnitTest {

    @Test
    public void whenOnJava9_thenJava9ConditionShouldPass() {

        try (MockedStatic<ConditionalUtils> theMock = Mockito.mockStatic(ConditionalUtils.class)) {
            theMock.when(ConditionalUtils::isJava9)
              .thenReturn(true);
            Assertions.assertTrue(
              new Java9Condition().matches(
                Mockito.mock(ConditionContext.class), Mockito.mock(AnnotatedTypeMetadata.class)
              )
            );
        }

    }

    @Test
    public void whenNotOnJava9_thenJava9ConditionShouldNotPass() {
        try (MockedStatic<ConditionalUtils> theMock = Mockito.mockStatic(ConditionalUtils.class)) {
            theMock.when(ConditionalUtils::isJava9)
              .thenReturn(false);
            Assertions.assertFalse(
              new Java9Condition().matches(
                Mockito.mock(ConditionContext.class), Mockito.mock(AnnotatedTypeMetadata.class)
              )
            );
        }
    }

}
