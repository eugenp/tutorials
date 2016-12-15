package org.baeldung.spring_batch_partitioner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.baeldung.spring_batch_intro.SpringBatchConfig;
import org.baeldung.spring_batch_intro.SpringConfig;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppPartitioner {
    private static final Log log = LogFactory.getLog(AppPartitioner.class);

    public static void main(final String[] args) {
        // Spring Java config
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(SpringConfig.class);
        context.register(SpringBatchConfig.class);
        context.refresh();

        // Spring xml config
        // ApplicationContext context = new ClassPathXmlApplicationContext("spring-batch.xml");

        final JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        final Job job = (Job) context.getBean("partitioningJob");
        log.info("Starting the batch job");
        try {
            final JobExecution execution = jobLauncher.run(job, new JobParameters());
        } catch (final Exception e) {
            e.printStackTrace();
            log.info("Job failed");
        }
    }
}