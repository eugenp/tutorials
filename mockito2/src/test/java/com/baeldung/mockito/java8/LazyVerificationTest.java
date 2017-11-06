package com.baeldung.mockito.java8;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.VerificationCollector;

public class LazyVerificationTest {
    @Rule
    public VerificationCollector verificationCollector = MockitoJUnit.collector();

    @Test
    public void testLazyVerification() throws Exception {
        List mockList = mock(ArrayList.class);
        mockList.add("one");
        mockList.clear();
        verify(mockList).add("one");
        verify(mockList).clear();
    }
}
