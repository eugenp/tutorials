package org.baeldung.mockito.misusing;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.exceptions.misusing.NotAMockException;

public class MockitoMisusingUnitTest {

    @Test
    public void givenNotASpy_whenDoReturn_thenThrowNotAMock() {
        try {
            List<String> list = new ArrayList<String>();
            Mockito.doReturn(100)
                .when(list)
                .size();

            fail("Should have thrown a NotAMockException because 'list' is not a mock!");
        } catch (NotAMockException e) {
            assertThat(e.getMessage(), containsString("Argument passed to when() is not a mock!"));
        }
    }
}
