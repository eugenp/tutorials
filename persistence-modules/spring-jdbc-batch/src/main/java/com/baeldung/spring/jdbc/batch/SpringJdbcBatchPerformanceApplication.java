package com.baeldung.spring.jdbc.batch;

import com.baeldung.spring.jdbc.batch.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringJdbcBatchPerformanceApplication implements CommandLineRunner {

    @Autowired
    @Qualifier("batchProductService")
    private ProductService batchProductService;
    @Autowired
    @Qualifier("simpleProductService")
    private ProductService simpleProductService;

    public static void main(String[] args) {
        SpringApplication.run(SpringJdbcBatchPerformanceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        int[] recordCounts = { 1, 10, 100, 1000, 10_000, 100_000, 1000_000 };

        for (int recordCount : recordCounts) {
            long regularElapsedTime = simpleProductService.createProducts(recordCount);
            long batchElapsedTime = batchProductService.createProducts(recordCount);

            System.out.println("-".repeat(50));
            System.out.format("%-20s%-5s%-10s%-5s%8sms\n", "Regular inserts", "|", recordCount, "|", regularElapsedTime);
            System.out.format("%-20s%-5s%-10s%-5s%8sms\n", "Batch inserts", "|", recordCount, "|", batchElapsedTime);
            System.out.printf("Total gain: %d %s\n", calculateGainInPercent(regularElapsedTime, batchElapsedTime), "%");
        }

    }

    int calculateGainInPercent(long before, long after) {
        return (int) Math.floor(100D * (before - after) / before);
    }
}
