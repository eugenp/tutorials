package com.baeldung.batchscheduler;

import com.baeldung.batchscheduler.SpringBatchScheduler;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.awaitility.Awaitility.await;
import static java.util.concurrent.TimeUnit.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringBatchScheduler.class)
public class SpringBatchSchedulerIntegrationTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void stopJobsWhenSchedulerDisabled() throws Exception {
        SpringBatchScheduler schedulerBean = context.getBean(SpringBatchScheduler.class);
        await().untilAsserted(() -> Assert.assertEquals(2, schedulerBean.getBatchRunCounter()
            .get()));
        schedulerBean.stop();
        await().atLeast(3, SECONDS);

        Assert.assertEquals(2, schedulerBean.getBatchRunCounter()
            .get());
    }

    @Test
    public void stopJobSchedulerWhenSchedulerDestroyed() throws Exception {
        ScheduledAnnotationBeanPostProcessor bean = context.getBean(ScheduledAnnotationBeanPostProcessor.class);
        SpringBatchScheduler schedulerBean = context.getBean(SpringBatchScheduler.class);
        await().untilAsserted(() -> Assert.assertEquals(2, schedulerBean.getBatchRunCounter()
            .get()));
        bean.postProcessBeforeDestruction(schedulerBean, "SpringBatchScheduler");
        await().atLeast(3, SECONDS);

        Assert.assertEquals(2, schedulerBean.getBatchRunCounter()
            .get());
    }

    @Test
    public void stopJobSchedulerWhenFutureTasksCancelled() throws Exception {
        SpringBatchScheduler schedulerBean = context.getBean(SpringBatchScheduler.class);
        await().untilAsserted(() -> Assert.assertEquals(2, schedulerBean.getBatchRunCounter()
            .get()));
        schedulerBean.cancelFutureSchedulerTasks();
        await().atLeast(3, SECONDS);

        Assert.assertEquals(2, schedulerBean.getBatchRunCounter()
            .get());
    }


}
