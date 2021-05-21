package com.baeldung.annotations.conditional;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class IsWindowsConditionUnitTest {

    @Test
    public void whenOnWindows_thenIsWindowsConditionShouldPass() {

        try (MockedStatic<ConditionalUtils> theMock = Mockito.mockStatic(ConditionalUtils.class)) {
            theMock.when(ConditionalUtils::isWindows)
              .thenReturn(true);
            Assertions.assertTrue(
              new IsWindowsCondition().matches(
                Mockito.mock(ConditionContext.class), Mockito.mock(AnnotatedTypeMetadata.class)
              )
            );
        }

    }

    @Test
    public void whenNotOnWindows_thenIsWindowsConditionShouldNotPass() {
        try (MockedStatic<ConditionalUtils> theMock = Mockito.mockStatic(ConditionalUtils.class)) {
            theMock.when(ConditionalUtils::isWindows)
              .thenReturn(false);
            Assertions.assertFalse(
              new IsWindowsCondition().matches(
                Mockito.mock(ConditionContext.class), Mockito.mock(AnnotatedTypeMetadata.class)
              )
            );
        }
    }

}
