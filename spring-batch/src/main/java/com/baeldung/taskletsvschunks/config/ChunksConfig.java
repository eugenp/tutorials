package com.baeldung.taskletsvschunks.config;

import com.baeldung.taskletsvschunks.chunks.LineProcessor;
import com.baeldung.taskletsvschunks.chunks.LineReader;
import com.baeldung.taskletsvschunks.chunks.LinesWriter;
import com.baeldung.taskletsvschunks.model.Line;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ChunksConfig {

    @Bean
    public ItemReader<Line> itemReader() {
        return new LineReader();
    }

    @Bean
    public ItemProcessor<Line, Line> itemProcessor() {
        return new LineProcessor();
    }

    @Bean
    public ItemWriter<Line> itemWriter() {
        return new LinesWriter();
    }

    @Bean(name = "processLines")
    protected Step processLines(JobRepository jobRepository, PlatformTransactionManager transactionManager, ItemReader<Line> reader, ItemProcessor<Line, Line> processor, ItemWriter<Line> writer) {
        return new StepBuilder("processLines", jobRepository).<Line, Line> chunk(2, transactionManager)
          .reader(reader)
          .processor(processor)
          .writer(writer)
          .build();
    }

    @Bean(name = "chunksJob")
    public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("chunksJob", jobRepository)
          .start(processLines(jobRepository, transactionManager, itemReader(), itemProcessor(), itemWriter()))
          .build();
    }

}
