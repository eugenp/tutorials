package com.baeldung.pointcutadvice;

import com.baeldung.TestConfig;
import com.baeldung.pointcutadvice.dao.FooDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class}, loader = AnnotationConfigContextLoader.class)
public class AopLoggingIntegrationTest {

    @Before
    public void setUp() {
        messages = new ArrayList<>();

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

        Logger logger = Logger.getLogger(LoggingAspect.class.getName());
        logger.addHandler(logEventHandler);
    }

    @Autowired
    private FooDao dao;

    private Handler logEventHandler;

    private List<String> messages;

    @Test
    public void givenLoggingAspect_whenCallDaoMethod_thenBeforeAdviceIsCalled() {
        dao.findById(1L);
        assertThat(messages, hasSize(1));

        String logMessage = messages.get(0);
        Pattern pattern = Pattern.compile("^\\[\\d{4}\\-\\d{2}\\-\\d{2} \\d{2}:\\d{2}:\\d{2}:\\d{3}\\]findById$");
        assertTrue(pattern.matcher(logMessage).matches());
    }

    @Test
    public void givenLoggingAspect_whenCallLoggableAnnotatedMethod_thenMethodIsLogged() {
        dao.create(42L, "baz");
        assertThat(messages, hasItem("Executing method: create"));
    }

    @Test
    public void givenLoggingAspect_whenCallMethodAcceptingAnnotatedArgument_thenArgumentIsLogged() {
        Foo foo = new Foo(42L, "baz");
        dao.merge(foo);
        assertThat(messages, hasItem("Accepting beans with @Entity annotation: " + foo));
    }
}
