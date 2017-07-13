package com.baeldung.couchbase.async;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AsyncIntegrationTestConfig.class })
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class })
public abstract class AsyncIntegrationTest {
}
