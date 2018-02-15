package com.baeldung.spring.cloud.aws;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.spring.cloud.aws.s3.SpringCloudS3Service;

@Configuration
@EnableAutoConfiguration
@ComponentScan("com.baeldung.spring.cloud.aws.s3")
public class InstanceProfileAwsApplication {

    private static final Logger logger = LoggerFactory.getLogger(InstanceProfileAwsApplication.class);
    private static final String applicationConfig = "spring.config.name:application-instance-profile";

    private static String bucketName;
    private static String fileName = "sample-file.txt";

    private static void setupResources() {
        bucketName = "baeldung-test-" + UUID.randomUUID()
            .toString();
        try {
            Files.write(Paths.get(fileName), "Hello World!".getBytes());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        setupResources();
        if (!new File(fileName).exists()) {
            logger.warn("Not able to create {} file. Check your folder permissions.", fileName);
            System.exit(1);
        }

        SpringApplication application = new SpringApplicationBuilder(InstanceProfileAwsApplication.class).properties(applicationConfig)
            .build();
        ConfigurableApplicationContext context = application.run(args);
        SpringCloudS3Service service = context.getBean(SpringCloudS3Service.class);

        // S3 bucket operations
        service.createBucket(bucketName);
        service.uploadObject(bucketName, fileName);
        service.downloadObject(bucketName, fileName);
        service.deleteBucket(bucketName);
    }

}
