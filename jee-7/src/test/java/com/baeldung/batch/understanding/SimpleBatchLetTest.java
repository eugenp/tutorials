package com.baeldung.batch.understanding;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Properties;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.JobExecution;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
class SimpleBatchLetTest {

	@Deployment
	public static WebArchive createDeployment() {

		WebArchive war = null;
		try {
			war = org.jboss.shrinkwrap.api.ShrinkWrap.create(WebArchive.class)
					.addClass(BatchTestHelper.class).addClass(SimpleBatchLet.class)
					.addAsWebInfResource(org.jboss.shrinkwrap.api.asset.EmptyAsset.INSTANCE, org.jboss.shrinkwrap.api.ArchivePaths.create("beans.xml"))
					.addAsResource("META-INF/batch-jobs/firstBatchJob.xml");

			System.out.println(war.toString(true));
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return war;
	}

	@Test
	public void testBatchletProcess() throws Exception {

		JobOperator jobOperator = BatchRuntime.getJobOperator();
		Long executionId = jobOperator.start("firstBatchJob", new Properties());
		JobExecution jobExecution = jobOperator.getJobExecution(executionId);

		jobExecution = BatchTestHelper.keepTestAlive(jobExecution);

		// <1> Job should be completed.
		assertEquals(jobExecution.getBatchStatus(), BatchStatus.COMPLETED);
	}
}
