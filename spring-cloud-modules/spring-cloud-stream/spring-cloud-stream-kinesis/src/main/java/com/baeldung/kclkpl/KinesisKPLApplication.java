package com.baeldung.kclkpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.producer.KinesisProducer;
import com.amazonaws.services.kinesis.producer.KinesisProducerConfiguration;

@SpringBootApplication
public class KinesisKPLApplication {

    @Value("${aws.access.key}")
    private String accessKey;

    @Value("${aws.secret.key}")
    private String secretKey;

    public static void main(String[] args) {
        SpringApplication.run(KinesisKPLApplication.class, args);
    }

    @Bean
    public KinesisProducer kinesisProducer() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        KinesisProducerConfiguration producerConfig = new KinesisProducerConfiguration().setCredentialsProvider(new AWSStaticCredentialsProvider(awsCredentials))
          .setVerifyCertificate(false)
          .setRegion(Regions.EU_CENTRAL_1.getName());

        return new KinesisProducer(producerConfig);
    }

}