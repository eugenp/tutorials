package com.baeldung.batchscheduler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.test.annotation.DirtiesContext;

import static org.awaitility.Awaitility.await;
import static java.util.concurrent.TimeUnit.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext
@PropertySource("classpath:application.properties")
public class SpringBatchSchedulerIntegrationTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void stopJobsWhenSchedulerDisabled() {
        SpringBatchScheduler schedulerBean = context.getBean(SpringBatchScheduler.class);
        await().untilAsserted(() -> assertEquals(2, schedulerBean.getBatchRunCounter()
                .get()));
        schedulerBean.stop();
        await().atLeast(3, SECONDS);

        assertEquals(2, schedulerBean.getBatchRunCounter().get());
    }

    @Test
    public void stopJobSchedulerWhenSchedulerDestroyed() {
        ScheduledAnnotationBeanPostProcessor bean = context.getBean(ScheduledAnnotationBeanPostProcessor.class);
        SpringBatchScheduler schedulerBean = context.getBean(SpringBatchScheduler.class);
        await().untilAsserted(() -> assertEquals(2, schedulerBean.getBatchRunCounter()
                .get()));
        bean.postProcessBeforeDestruction(schedulerBean, "SpringBatchScheduler");
        await().atLeast(3, SECONDS);

        assertEquals(2, schedulerBean.getBatchRunCounter()
                .get());
    }

    @Test
    public void stopJobSchedulerWhenFutureTasksCancelled() {
        SpringBatchScheduler schedulerBean = context.getBean(SpringBatchScheduler.class);
        await().untilAsserted(() -> assertEquals(2, schedulerBean.getBatchRunCounter()
                .get()));
        schedulerBean.cancelFutureSchedulerTasks();
        await().atLeast(3, SECONDS);

        assertEquals(2, schedulerBean.getBatchRunCounter()
                .get());
    }


}
