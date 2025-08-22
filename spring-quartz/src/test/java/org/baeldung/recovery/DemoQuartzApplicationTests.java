package org.baeldung.recovery;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class DemoQuartzApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private Scheduler scheduler;

	@Test
	void whenAppRestarts_thenSampleJobIsReloaded() throws Exception {
		JobKey jobKey = new JobKey("sampleJob", "group1");   // same as in your config
		TriggerKey triggerKey = new TriggerKey("sampleTrigger", "group1");

		// --- First check: job exists in running scheduler
		JobDetail jobDetail = scheduler.getJobDetail(jobKey);
		assertNotNull(jobDetail, "SampleJob should be scheduled by Spring Boot");

		Trigger trigger = scheduler.getTrigger(triggerKey);
		assertNotNull(trigger, "Trigger should be scheduled by Spring Boot");

		// --- Simulate shutdown
		scheduler.standby();

		// --- Simulate restart: create a new scheduler instance (normally Boot does this on startup)
//		Scheduler restartedScheduler = StdSchedulerFactory.getDefaultScheduler();
//		restartedScheduler.start();
		/*SchedulerFactory factory = new StdSchedulerFactory("application-test.properties");
		Scheduler restartedScheduler = factory.getScheduler();
		restartedScheduler.start();*/
		Scheduler restartedScheduler = applicationContext.getBean(Scheduler.class);
		restartedScheduler.start();
//		assertNotNull(restartedScheduler.getJobDetail(jobKey),
//			"SampleJob should be reloaded from DB after restart");

//		Scheduler restartedScheduler = context.getBean(Scheduler.class);
		assertTrue(restartedScheduler.isStarted(), "Scheduler should be running after restart");


		// --- Verify Quartz reloaded job and trigger from DB
		JobDetail reloadedJob = restartedScheduler.getJobDetail(jobKey);
		assertNotNull(reloadedJob, "SampleJob should be reloaded from DB after restart");

		Trigger reloadedTrigger = restartedScheduler.getTrigger(triggerKey);
		assertNotNull(reloadedTrigger, "Trigger should be reloaded from DB after restart");
	}
}
