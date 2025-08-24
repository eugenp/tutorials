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
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("recovery")
@SpringBootTest(classes = SpringQuartzRecoveryApp.class)
class SpringQuartzRecoveryAppUnitTest {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private Scheduler scheduler;

	@Test
	void givenSampleJob_whenSchedulerRestart_thenSampleJobIsReloaded() throws Exception {
		// Given
		JobKey jobKey = new JobKey("sampleJob", "group1");
		TriggerKey triggerKey = new TriggerKey("sampleTrigger", "group1");

		JobDetail jobDetail = scheduler.getJobDetail(jobKey);
		assertNotNull(jobDetail, "SampleJob exists in running scheduler");

		Trigger trigger = scheduler.getTrigger(triggerKey);
		assertNotNull(trigger, "SampleTrigger exists in running scheduler");

		// When
		scheduler.standby();
		Scheduler restartedScheduler = applicationContext.getBean(Scheduler.class);
		restartedScheduler.start();

		// Then
		assertTrue(restartedScheduler.isStarted(), "Scheduler should be running after restart");

		JobDetail reloadedJob = restartedScheduler.getJobDetail(jobKey);
		assertNotNull(reloadedJob, "SampleJob should be reloaded from DB after restart");

		Trigger reloadedTrigger = restartedScheduler.getTrigger(triggerKey);
		assertNotNull(reloadedTrigger, "SampleTrigger should be reloaded from DB after restart");
	}

}
