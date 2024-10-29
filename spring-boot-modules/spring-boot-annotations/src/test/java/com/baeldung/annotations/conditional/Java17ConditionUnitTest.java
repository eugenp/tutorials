package com.baeldung.annotations.conditional;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class Java17ConditionUnitTest {

    @Test
        public void whenOnJava17_thenJava7ConditionShouldPass() {
        try (MockedStatic<ConditionalUtils> theMock = Mockito.mockStatic(ConditionalUtils.class)) {
            theMock.when(ConditionalUtils::isJava17)
              .thenReturn(true);
            Assertions.assertTrue(
              new Java17Condition().matches(
                Mockito.mock(ConditionContext.class), Mockito.mock(AnnotatedTypeMetadata.class)
              )
            );
        }

    }

    @Test
    public void whenNotOnJava17_thenJava17ConditionShouldNotPass() {
        try (MockedStatic<ConditionalUtils> theMock = Mockito.mockStatic(ConditionalUtils.class)) {
            theMock.when(ConditionalUtils::isJava17)
              .thenReturn(false);
            Assertions.assertFalse(
              new Java17Condition().matches(
                Mockito.mock(ConditionContext.class), Mockito.mock(AnnotatedTypeMetadata.class)
              )
            );
        }
    }

}
