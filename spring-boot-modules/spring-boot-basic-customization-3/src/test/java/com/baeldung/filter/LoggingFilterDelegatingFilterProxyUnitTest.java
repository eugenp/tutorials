package com.baeldung.filter;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoggingFilterDelegatingFilterProxyUnitTest {

    @Autowired
    private LoggingFilterDelegatingFilterProxy loggingFilter;

    @Test
    public void testLoggingFilter() throws Exception {
        Assert.assertNotNull(loggingFilter);
        Assert.assertNotNull(getField(loggingFilter,"loggingService"));
    }

    private Object getField(Object target, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(target);
    }
}
