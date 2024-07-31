package com.baeldung.java9.language;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TryWithResourcesUnitTest {

    static int closeCount = 0;

    static class MyAutoCloseable implements AutoCloseable {
        final FinalWrapper finalWrapper = new FinalWrapper();

        public void close() {
            closeCount++;
        }

        static class FinalWrapper {
            public final AutoCloseable finalCloseable = new AutoCloseable() {
                @Override
                public void close() throws Exception {
                    closeCount++;
                }
            };
        }
    }

    @Test
    public void tryWithResourcesTest() {
        MyAutoCloseable mac = new MyAutoCloseable();

        try (mac) {
            assertEquals("Expected and Actual does not match", 0, closeCount);
        }

        try (mac.finalWrapper.finalCloseable) {
            assertEquals("Expected and Actual does not match", 1, closeCount);
        } catch (Exception ex) {
        }

        try (new MyAutoCloseable() { }.finalWrapper.finalCloseable) {
            assertEquals("Expected and Actual does not match", 2, closeCount);
        } catch (Exception ex) {
        }

        try ((closeCount > 0 ? mac : new MyAutoCloseable()).finalWrapper.finalCloseable) {
            assertEquals("Expected and Actual does not match", 3, closeCount);
        } catch (Exception ex) {
        }

        try {
            throw new CloseableException();
        } catch (CloseableException ex) {
            try (ex) {
                assertEquals("Expected and Actual does not match", 4, closeCount);
            }
        }
        assertEquals("Expected and Actual does not match", 5, closeCount);
    }

    static class CloseableException extends Exception implements AutoCloseable {
        @Override
        public void close() {
            closeCount++;
        }
    }

}
