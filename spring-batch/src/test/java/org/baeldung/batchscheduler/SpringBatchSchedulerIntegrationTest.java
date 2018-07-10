package org.baeldung.batchscheduler;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringBatchScheduler.class)
public class SpringBatchSchedulerIntegrationTest {

    private static final int TIMER = 3000;

    @Autowired
    private ApplicationContext context;

    @Test
    public void stopJobsWhenSchedulerDisabled() throws Exception {
        Thread.sleep(TIMER);
        SpringBatchScheduler schedulerBean = context.getBean(SpringBatchScheduler.class);
        schedulerBean.stop();
        Thread.sleep(TIMER);
        Date lastLaunchDate = schedulerBean.getCurrentLaunchDate();
        Thread.sleep(TIMER);
        Assert.assertEquals(lastLaunchDate, schedulerBean.getCurrentLaunchDate());
    }

    @Test
    public void stopJobSchedulerWhenSchedulerDestroyed() throws Exception {
        Thread.sleep(TIMER);
        ScheduledAnnotationBeanPostProcessor bean = context.getBean(ScheduledAnnotationBeanPostProcessor.class);
        SpringBatchScheduler schedulerBean = context.getBean(SpringBatchScheduler.class);
        bean.postProcessBeforeDestruction(schedulerBean, "SpringBatchScheduler");
        Thread.sleep(TIMER);
        Date lastLaunchTime = schedulerBean.getCurrentLaunchDate();
        Thread.sleep(TIMER);
        Assert.assertEquals(lastLaunchTime, schedulerBean.getCurrentLaunchDate());

    }

    @Test
    public void stopJobSchedulerWhenFutureTasksCancelled() throws Exception {
        Thread.sleep(TIMER);
        SpringBatchScheduler schedulerBean = context.getBean(SpringBatchScheduler.class);
        schedulerBean.cancelFutureSchedulerTasks();
        Thread.sleep(TIMER);
        Date lastLaunchTime = schedulerBean.getCurrentLaunchDate();
        Thread.sleep(TIMER);
        Assert.assertEquals(lastLaunchTime, schedulerBean.getCurrentLaunchDate());

    }
    
}
