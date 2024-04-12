package com.baeldung.singleton;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

class SingletonUnitTest {

    @Test
    void givenTwoValues_whenSum_thenReturnCorrectResult() {
        SingletonDemo singletonDemo = new SingletonDemo();
        int result = singletonDemo.sum(12, 4);
        Assertions.assertEquals(16, result);
    }

    @Test
    void givenMockedLogger_whenSum_thenReturnCorrectResult() {
        Logger logger = mock(Logger.class);

        try (MockedStatic<Logger> loggerMockedStatic = mockStatic(Logger.class)) {
            loggerMockedStatic.when(Logger::getInstance).thenReturn(logger);
            doNothing().when(logger).log(any());

            SingletonDemo singletonDemo = new SingletonDemo();
            int result = singletonDemo.sum(12, 4);
            Assertions.assertEquals(16, result);
        }
    }

}
