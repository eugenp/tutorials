package com.baeldung.netflix.mantis;

import com.baeldung.netflix.mantis.job.LogAggregationJob;
import io.mantisrx.runtime.executor.LocalJobExecutorNetworked;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class MantisApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MantisApplication.class, args);
    }

    @Override
    public void run(String... args) {
        LocalJobExecutorNetworked.execute(new LogAggregationJob().getJobInstance());
    }

}
