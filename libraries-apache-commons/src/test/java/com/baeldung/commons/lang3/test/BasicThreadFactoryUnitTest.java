package com.baeldung.commons.lang3.test;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class BasicThreadFactoryUnitTest {

    @Test
    public void givenBasicThreadFactoryInstance_whenCalledBuilder_thenCorrect() {
        BasicThreadFactory factory = new BasicThreadFactory.Builder()
            .namingPattern("workerthread-%d")
            .daemon(true)
            .priority(Thread.MAX_PRIORITY)
            .build();
        assertThat(factory).isInstanceOf(BasicThreadFactory.class);
    }
}
