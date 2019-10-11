package org.baeldung.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
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
    }

    private static void runJob(AnnotationConfigApplicationContext context, String batchJobName) {
        final JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        final Job job = (Job) context.getBean(batchJobName);

        System.out.println("----------------------------------------");
        System.out.println("Starting the batch job: " + batchJobName);
        try {
			// To enable multiple execution of a job with the same parameters
			JobParameters jobParameters = new JobParametersBuilder()
                    .addString("jobID", String.valueOf(System.currentTimeMillis()))
                    .toJobParameters();
            final JobExecution execution = jobLauncher.run(job, jobParameters);
            System.out.println("Job Status : " + execution.getStatus());
            System.out.println("Job succeeded");
        } catch (final Exception e) {
            e.printStackTrace();
            System.out.println("Job failed");
        }
    }
}