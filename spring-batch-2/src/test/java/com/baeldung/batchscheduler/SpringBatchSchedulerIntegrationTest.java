package com.baeldung.batchscheduler;

import com.baeldung.batchscheduler.SpringBatchScheduler;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.awaitility.Awaitility.await;
import static java.util.concurrent.TimeUnit.*;

@SpringBatchTest
@SpringBootTest
@DirtiesContext
@PropertySource("classpath:application.properties")
@RunWith(SpringRunner.class)
public class SpringBatchSchedulerIntegrationTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void stopJobsWhenSchedulerDisabled() {
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
