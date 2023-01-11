package com.baeldung.kclkpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;

@SpringBootApplication
public class KinesisKCLApplication implements ApplicationRunner {

    @Value("${aws.access.key}")
    private String accessKey;
    
    @Value("${aws.secret.key}")
    private String secretKey;
    
    @Value("${ips.stream}")
    private String IPS_STREAM;
    
    public static void main(String[] args) {
        SpringApplication.run(KinesisKCLApplication.class, args);
    }

	@Override
	public void run(ApplicationArguments args) throws Exception {
		BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
		KinesisClientLibConfiguration consumerConfig = new KinesisClientLibConfiguration(
		  "KinesisKCLConsumer", 
		  IPS_STREAM,
		  new AWSStaticCredentialsProvider(awsCredentials), 
		  "KinesisKCLConsumer")
		    .withRegionName(Regions.EU_CENTRAL_1.getName());

		new Worker.Builder()
		  .recordProcessorFactory(new IpProcessorFactory())
		  .config(consumerConfig)
		  .build()
		  .run();
	}
    
}