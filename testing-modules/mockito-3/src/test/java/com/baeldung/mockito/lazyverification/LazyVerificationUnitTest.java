package com.baeldung.mockito.lazyverification;

import org.junit.Test;
import org.mockito.exceptions.base.MockitoAssertionError;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.VerificationCollector;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LazyVerificationUnitTest {

    @Test
    public void whenLazilyVerified_thenReportsMultipleFailures() {
        VerificationCollector collector = MockitoJUnit.collector()
            .assertLazily();

        List mockList = mock(List.class);
        verify(mockList).add("one");
        verify(mockList).clear();

        try {
            collector.collectAndReport();
        } catch (MockitoAssertionError error) {
            assertTrue(error.getMessage()
                .contains("1. Wanted but not invoked:"));
            assertTrue(error.getMessage()
                .contains("2. Wanted but not invoked:"));
        }
    }
}
