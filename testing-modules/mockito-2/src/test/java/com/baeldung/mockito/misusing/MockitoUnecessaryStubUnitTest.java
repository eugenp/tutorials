package com.baeldung.mockito.misusing;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.misusing.UnnecessaryStubbingException;
import org.mockito.junit.MockitoJUnit;
import org.mockito.quality.Strictness;

public class MockitoUnecessaryStubUnitTest {

    @Rule
    public ExpectedTestFailureRule rule = new ExpectedTestFailureRule(MockitoJUnit.rule()
        .strictness(Strictness.STRICT_STUBS));

    @Mock
    private ArrayList<String> mockList;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenUnusedStub_whenInvokingGetThenThrowUnnecessaryStubbingException() {
        rule.expectedFailure(UnnecessaryStubbingException.class);

        when(mockList.add("one")).thenReturn(true);
        when(mockList.get(anyInt())).thenReturn("hello");

        assertEquals("List should contain hello", "hello", mockList.get(1));
    }

    @Test
    public void givenLenientdStub_whenInvokingGetThenDontThrowUnnecessaryStubbingException() {
        lenient().when(mockList.add("one"))
            .thenReturn(true);
        when(mockList.get(anyInt())).thenReturn("hello");

        assertEquals("List should contain hello", "hello", mockList.get(1));
    }

}
