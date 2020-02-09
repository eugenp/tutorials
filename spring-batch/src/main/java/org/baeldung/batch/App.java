package org.baeldung.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(final String[] args) {
        // Spring Java config
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(SpringConfig.class);
        context.register(SpringBatchConfig.class);
        context.refresh();

        // Spring xml config
        // ApplicationContext context = new ClassPathXmlApplicationContext("spring-batch.xml");

        runJob(context, "firstBatchJob");
        runJob(context, "skippingBatchJob");
        runJob(context, "skipPolicyBatchJob");
        runJob(context, "retryBatchJob");

    }

    private static void runJob(AnnotationConfigApplicationContext context, String batchJobName) {
        final JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        final Job job = (Job) context.getBean(batchJobName);

        LOGGER.info("Starting the batch job: {}", batchJobName);
        try {
            // To enable multiple execution of a job with the same parameters
            JobParameters jobParameters = new JobParametersBuilder().addString("jobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
            final JobExecution execution = jobLauncher.run(job, jobParameters);
            LOGGER.info("Job Status : {}", execution.getStatus());
        } catch (final Exception e) {
            e.printStackTrace();
            LOGGER.error("Job failed {}", e.getMessage());
        }
    }
}