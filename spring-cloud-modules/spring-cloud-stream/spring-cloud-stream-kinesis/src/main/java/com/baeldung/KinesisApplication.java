package com.baeldung;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.support.MessageBuilder;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;

@SpringBootApplication
@EnableBinding(Processor.class)
public class KinesisApplication {

    @Value("${aws.access.key}")
    private String accessKey;
    
    @Value("${aws.secret.key}")
    private String secretKey;
    
    @Autowired
    private Processor processor;

    public static void main(String[] args) {
        SpringApplication.run(KinesisApplication.class, args);
    }

    @Bean
    public AmazonKinesis buildAmazonKinesis() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonKinesisClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
            .withRegion(Regions.EU_CENTRAL_1)
            .build();
    }

    @StreamListener(Processor.INPUT)
    public void consume(String val) {
        System.out.println(val);
    }

    public void produce(String val) {
        processor.output().send(MessageBuilder.withPayload(val).build());
    }
}