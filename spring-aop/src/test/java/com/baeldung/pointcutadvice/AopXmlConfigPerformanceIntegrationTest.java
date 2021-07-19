package com.baeldung.pointcutadvice;

import com.baeldung.pointcutadvice.dao.FooDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/pointcutadvice/beans.xml")
public class AopXmlConfigPerformanceIntegrationTest {

    @Before
    public void setUp() {
        logEventHandler = new Handler() {
            @Override
            public void publish(LogRecord record) {
                messages.add(record.getMessage());
            }

            @Override
            public void flush() {
            }

            @Override
            public void close() throws SecurityException {
            }
        };

        messages = new ArrayList<>();
    }

    @Autowired
    private FooDao dao;

    private Handler logEventHandler;

    private List<String> messages;

    @Test
    public void givenPerformanceAspect_whenCallDaoMethod_thenPerformanceMeasurementAdviceIsCalled() {
        Logger logger = Logger.getLogger(PerformanceAspect.class.getName());
        logger.addHandler(logEventHandler);

        final String entity = dao.findById(1L);
        assertThat(entity, notNullValue());
        assertThat(messages, hasSize(1));

        String logMessage = messages.get(0);
        Pattern pattern = Pattern.compile("Execution of findById took \\d+ ms");
        assertTrue(pattern.matcher(logMessage).matches());
    }
}
