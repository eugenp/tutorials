package com.baeldung.exceptionininitializererror;

import org.junit.Test;

import java.lang.reflect.Constructor;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ExceptionInInitializerErrorUnitTest {

    @Test
    public void givenStaticVar_whenThrows_thenWrapsItInAnExceptionInInitializerError() {
        assertThatThrownBy(StaticVar::new)
          .isInstanceOf(ExceptionInInitializerError.class)
          .hasCauseInstanceOf(RuntimeException.class);
    }

    @Test
    public void givenStaticBlock_whenThrows_thenWrapsItInAnExceptionInInitializerError() {
        assertThatThrownBy(StaticBlock::new)
          .isInstanceOf(ExceptionInInitializerError.class)
          .hasCauseInstanceOf(ArithmeticException.class);
    }

    private static class CheckedConvention {

        private static Constructor<?> constructor;

        static {
            try {
                constructor = CheckedConvention.class.getDeclaredConstructor();
            } catch (NoSuchMethodException e) {
                throw new ExceptionInInitializerError(e);
            }
        }
    }

    private static class StaticVar {

        private static int state = initializeState();

        private static int initializeState() {
            throw new RuntimeException();
        }
    }

    private static class StaticBlock {

        private static int state;

        static {
            state = 42 / 0;
        }
    }
}
