package com.baeldung.mockito.misusing;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.exceptions.misusing.UnnecessaryStubbingException;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.quality.Strictness;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MockitoUnecessaryStubUnitTest {

    @Rule
    public ExpectedTestFailureRule rule = new ExpectedTestFailureRule(MockitoJUnit.rule()
        .strictness(Strictness.STRICT_STUBS));

    @Mock
    private ArrayList<String> mockList;

    @Test
    public void givenUnusedStub_whenInvokingGetThenThrowUnnecessaryStubbingException() {
        rule.expectedFailure(UnnecessaryStubbingException.class);

        // Commenting this stubbing so that it doesn't affect the builds.
        // If you want to reproduce UnnecessaryStubbingException then uncomment below line and execute the test.
        // when(mockList.add("one")).thenReturn(true);
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
