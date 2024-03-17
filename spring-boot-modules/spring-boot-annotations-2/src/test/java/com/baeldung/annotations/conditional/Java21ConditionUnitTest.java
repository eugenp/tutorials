package com.baeldung.annotations.conditional;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class Java21ConditionUnitTest {

    @Test
    public void whenOnJava21_thenJava21ConditionShouldPass() {
        try (MockedStatic<ConditionalUtils> theMock = Mockito.mockStatic(ConditionalUtils.class)) {
            theMock.when(ConditionalUtils::isJava21)
              .thenReturn(true);
            Assertions.assertTrue(
              new Java21Condition().matches(
                Mockito.mock(ConditionContext.class), Mockito.mock(AnnotatedTypeMetadata.class)
              )
            );
        }

    }

    @Test
    public void whenNotOnJava21_thenJava21ConditionShouldNotPass() {
        try (MockedStatic<ConditionalUtils> theMock = Mockito.mockStatic(ConditionalUtils.class)) {
            theMock.when(ConditionalUtils::isJava21)
              .thenReturn(false);
            Assertions.assertFalse(
              new Java21Condition().matches(
                Mockito.mock(ConditionContext.class), Mockito.mock(AnnotatedTypeMetadata.class)
              )
            );
        }
    }

}
