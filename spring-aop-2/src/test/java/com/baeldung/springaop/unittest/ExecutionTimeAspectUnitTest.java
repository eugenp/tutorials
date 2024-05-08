package com.baeldung.springaop.unittest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExecutionTimeAspectUnitTest {

    @Mock
    private ProceedingJoinPoint joinPoint;

    @Mock
    private Logger logger;

    @InjectMocks
    private ExecutionTimeAspect aspect;

    @Test
    void whenExecuteJoinPoint_thenLoggerInfoIsCalled() throws Throwable {
        when(joinPoint.proceed()).thenReturn(null);
        aspect.logExecutionTime(joinPoint);
        verify(joinPoint, times(1)).proceed();
        verify(logger, times(1)).info(anyString());
    }

    @Test
    void whenExecuteJoinPoint_thenExecutionTimeIsLogged() throws Throwable {
        when(joinPoint.proceed()).thenReturn(null);
        aspect.logExecutionTime(joinPoint);
        verify(joinPoint, times(1)).proceed();

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(logger, times(1)).info(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).startsWith("Execution time=");
    }

}