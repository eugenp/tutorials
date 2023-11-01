package com.baeldung.setparam;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SanitizeParametersRequestWrapperUnitTest {

    private static final String NEW_VALUE = "NEW VALUE";

    private Map<String, String[]> parameterMap;

    @Mock
    private HttpServletRequest request;

    @Before
    public void initBeforeEachTest() {
        parameterMap = new HashMap<>();
        parameterMap.put("input", new String[] {"<script>alert('Hello');</script>"});
        when(request.getParameterMap()).thenReturn(parameterMap);
    }

    @Test
    public void whenGetParameterViaWrapper_thenParameterReturnedIsSanitized() {
        SanitizeParametersRequestWrapper wrapper = new SanitizeParametersRequestWrapper(request);
        String actualValue = wrapper.getParameter("input");

        assertEquals("&lt;script&gt;alert('Hello');&lt;/script&gt;", actualValue);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void whenPutValueToWrapperParameterMap_thenThrowsUnsupportedOperationException() {
        SanitizeParametersRequestWrapper wrapper = new SanitizeParametersRequestWrapper(request);
        Map<String, String[]> wrapperParamMap = wrapper.getParameterMap();
        wrapperParamMap.put("input", new String[] {NEW_VALUE});
    }

    @Test
    public void whenSetValueToWrapperParametersStringArray_thenThe2ndCallShouldNotEqualToNewValue() {
        SanitizeParametersRequestWrapper wrapper = new SanitizeParametersRequestWrapper(request);
        String[] firstCallValues = wrapper.getParameterValues("input");

        firstCallValues[0] = NEW_VALUE;
        String[] secondCallValues = wrapper.getParameterValues("input");
        assertThat(secondCallValues).doesNotContain(NEW_VALUE);
    }

}