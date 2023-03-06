package com.baeldung.kclkpl;

import static com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.amazonaws.ClientConfiguration;
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

        KinesisClientLibConfiguration consumerConfig = new KinesisClientLibConfiguration("KinesisKCLConsumer", IPS_STREAM, "", "", DEFAULT_INITIAL_POSITION_IN_STREAM, new AWSStaticCredentialsProvider(awsCredentials),
          new AWSStaticCredentialsProvider(awsCredentials), new AWSStaticCredentialsProvider(awsCredentials), DEFAULT_FAILOVER_TIME_MILLIS, "KinesisKCLConsumer", DEFAULT_MAX_RECORDS, DEFAULT_IDLETIME_BETWEEN_READS_MILLIS,
          DEFAULT_DONT_CALL_PROCESS_RECORDS_FOR_EMPTY_RECORD_LIST, DEFAULT_PARENT_SHARD_POLL_INTERVAL_MILLIS, DEFAULT_SHARD_SYNC_INTERVAL_MILLIS, DEFAULT_CLEANUP_LEASES_UPON_SHARDS_COMPLETION, new ClientConfiguration(), new ClientConfiguration(),
          new ClientConfiguration(), DEFAULT_TASK_BACKOFF_TIME_MILLIS, DEFAULT_METRICS_BUFFER_TIME_MILLIS, DEFAULT_METRICS_MAX_QUEUE_SIZE, DEFAULT_VALIDATE_SEQUENCE_NUMBER_BEFORE_CHECKPOINTING, Regions.EU_CENTRAL_1.getName(), DEFAULT_SHUTDOWN_GRACE_MILLIS,
          DEFAULT_DDB_BILLING_MODE, null, 0, 0, 0);

        new Worker.Builder().recordProcessorFactory(new IpProcessorFactory())
          .config(consumerConfig)
          .build()
          .run();
    }
}