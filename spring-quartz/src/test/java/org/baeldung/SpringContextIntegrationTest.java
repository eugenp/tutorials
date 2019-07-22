package org.baeldung;

import org.baeldung.springquartz.SpringQuartzApp;
import org.baeldung.springquartz.basics.service.SampleJobService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringQuartzApp.class)
public class SpringContextIntegrationTest {

    @Autowired
    private SampleJobService sampleJobService;

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }

    @Test
    public void whenSchedulerStarts_thenJobsRun() throws InterruptedException {
        assertThat(sampleJobService.getNumberOfInvocations()).isEqualTo(0);
        Thread.sleep(SampleJobService.EXECUTION_TIME);
        assertThat(sampleJobService.getNumberOfInvocations()).isEqualTo(1);
    }
}
