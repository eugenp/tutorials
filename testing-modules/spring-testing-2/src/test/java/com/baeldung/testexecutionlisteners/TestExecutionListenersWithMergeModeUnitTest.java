package com.baeldung.testexecutionlisteners;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@TestExecutionListeners(value = { CustomTestExecutionListener.class },
    mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
@ContextConfiguration(classes = AdditionService.class)
public class TestExecutionListenersWithMergeModeUnitTest {

    @Autowired
    private AdditionService additionService;

    @Test
    public void whenValidNumbersPassed_thenReturnSum() {
        assertThat(additionService.add(5, 13), Matchers.is(18));
    }
}
