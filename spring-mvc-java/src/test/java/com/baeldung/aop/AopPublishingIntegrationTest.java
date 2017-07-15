package com.baeldung.aop;

import com.baeldung.config.TestConfig;
import com.baeldung.dao.FooDao;
import com.baeldung.events.FooCreationEventListener;
import com.baeldung.model.Foo;
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

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class}, loader = AnnotationConfigContextLoader.class)
public class AopPublishingIntegrationTest {

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
    public void givenPublishingAspect_whenCallCreate_thenCreationEventIsPublished() {
        Logger logger = Logger.getLogger(FooCreationEventListener.class.getName());
        logger.addHandler(logEventHandler);

        dao.create(1L, "Bar");

        String logMessage = messages.get(0);
        Pattern pattern = Pattern.compile("Created foo instance: " + Pattern.quote(new Foo(1L, "Bar").toString()));
        assertTrue(pattern.matcher(logMessage).matches());
    }
}
