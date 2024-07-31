package com.baeldung.batchscheduler;

import com.baeldung.batchscheduler.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.ScheduledMethodRunnable;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Date;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
@EnableScheduling
public class SpringBatchScheduler {

    private final Logger logger = LoggerFactory.getLogger(SpringBatchScheduler.class);

    private AtomicBoolean enabled = new AtomicBoolean(true);

    private AtomicInteger batchRunCounter = new AtomicInteger(0);

    private final Map<Object, ScheduledFuture<?>> scheduledTasks = new IdentityHashMap<>();

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Scheduled(fixedRate = 2000)
    public void launchJob() throws Exception {
        Date date = new Date();
        logger.debug("scheduler starts at " + date);
        if (enabled.get()) {
            JobExecution jobExecution = jobLauncher.run(job(jobRepository, transactionManager), new JobParametersBuilder().addDate("launchDate", date)
                    .toJobParameters());
            batchRunCounter.incrementAndGet();
            logger.debug("Batch job ends with status as " + jobExecution.getStatus());
        }
        logger.debug("scheduler ends ");
    }

    public void stop() {
        enabled.set(false);
    }

    public void start() {
        enabled.set(true);
    }

    @Bean
    public TaskScheduler poolScheduler() {
        return new CustomTaskScheduler();
    }

    private class CustomTaskScheduler extends ThreadPoolTaskScheduler {

        private static final long serialVersionUID = -7142624085505040603L;

        @Override
        public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long period) {
            ScheduledFuture<?> future = super.scheduleAtFixedRate(task, period);

            ScheduledMethodRunnable runnable = (ScheduledMethodRunnable) task;
            scheduledTasks.put(runnable.getTarget(), future);

            return future;
        }

    }

    public void cancelFutureSchedulerTasks() {
        scheduledTasks.forEach((k, v) -> {
            if (k instanceof SpringBatchScheduler) {
                v.cancel(false);
            }
        });
    }

    @Bean
    public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("job", jobRepository)
                .start(readBooks(jobRepository, transactionManager))
                .build();
    }

    @Bean
    protected Step readBooks(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("readBooks", jobRepository)
                .<Book, Book> chunk(2, transactionManager)
                .reader(reader())
                .writer(writer())
                .build();
    }

    @Bean
    public FlatFileItemReader<Book> reader() {
        return new FlatFileItemReaderBuilder<Book>().name("bookItemReader")
                .resource(new ClassPathResource("books.csv"))
                .delimited()
                .names(new String[] { "id", "name" })
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {
                    {
                        setTargetType(Book.class);
                    }
                })
                .build();
    }

    @Bean
    public ItemWriter<Book> writer() {
        return items -> {
            logger.debug("writer..." + items.size());
            for (Book item : items) {
                logger.debug(item.toString());
            }
        };
    }

    public AtomicInteger getBatchRunCounter() {
        return batchRunCounter;
    }

}
