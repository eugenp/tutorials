package com.baeldung.setparam;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SetParameterRequestWrapperUnitTest {

    private static final String NEW_VALUE = "NEW VALUE";

    private Map<String, String[]> parameterMap;

    @Mock
    private HttpServletRequest request;

    @Before
    public void initBeforeEachTest() {
        parameterMap = new HashMap<>();
        parameterMap.put("input", new String[] {"inputValue"});
        when(request.getParameterMap()).thenReturn(parameterMap);
    }

    @Test
    public void whenSetParameterViaWrapper_thenGetParameterShouldReturnTheSameValue() {
        SetParameterRequestWrapper wrapper = new SetParameterRequestWrapper(request);
        wrapper.setParameter("newInput", "newInputValue");
        String actualValue = wrapper.getParameter("newInput");

        assertEquals("newInputValue", actualValue);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void whenPutValueToWrapperParameterMap_thenThrowsUnsupportedOperationException() {
        SetParameterRequestWrapper wrapper = new SetParameterRequestWrapper(request);
        Map<String, String[]> wrapperParamMap = wrapper.getParameterMap();
        wrapperParamMap.put("input", new String[] {NEW_VALUE});
    }

    @Test
    public void whenSetValueToWrapperParametersStringArray_thenThe2ndCallShouldNotEqualToNewValue() {
        SetParameterRequestWrapper wrapper = new SetParameterRequestWrapper(request);
        String[] firstCallValues = wrapper.getParameterValues("input");

        firstCallValues[0] = NEW_VALUE;
        String[] secondCallValues = wrapper.getParameterValues("input");
        assertThat(secondCallValues).doesNotContain(NEW_VALUE);
    }

}