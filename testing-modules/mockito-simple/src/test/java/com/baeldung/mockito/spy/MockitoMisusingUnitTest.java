package com.baeldung.mockito.spy;

import org.junit.After;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.exceptions.misusing.NotAMockException;
import org.mockito.internal.progress.ThreadSafeMockingProgress;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

public class MockitoMisusingUnitTest {

    @After
    public void tearDown() {
        ThreadSafeMockingProgress.mockingProgress().reset();
    }
    
    @Test
    public void givenNotASpy_whenDoReturn_thenThrowNotAMock() {
        try {
            List<String> list = new ArrayList<String>();

            Mockito.doReturn(100, Mockito.withSettings().lenient())
                .when(list)
                .size();

            fail("Should have thrown a NotAMockException because 'list' is not a mock!");
        } catch (NotAMockException e) {
            assertThat(e.getMessage(), containsString("Argument passed to when() is not a mock!"));
        }
    }

}
