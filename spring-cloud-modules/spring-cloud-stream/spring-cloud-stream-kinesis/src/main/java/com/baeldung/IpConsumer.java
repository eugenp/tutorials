package com.baeldung;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.model.GetRecordsRequest;
import com.amazonaws.services.kinesis.model.GetRecordsResult;
import com.amazonaws.services.kinesis.model.GetShardIteratorRequest;
import com.amazonaws.services.kinesis.model.GetShardIteratorResult;
import com.amazonaws.services.kinesis.model.ShardIteratorType;

@Component
@EnableBinding(Sink.class)
public class IpConsumer {

    @Value("${ips.stream}")
    private String IPS_STREAM;
    
    @Value("${ips.shard.id}")
    private String IPS_SHARD_ID;
    
    @Autowired
    private AmazonKinesis kinesis;
    
    private GetShardIteratorResult shardIterator;

    @StreamListener(Sink.INPUT)
    public void consume(String ip) {
        System.out.println(ip);
    }

    private void consumeWithKinesis() {
        GetRecordsRequest recordsRequest = new GetRecordsRequest();
        recordsRequest.setShardIterator(shardIterator.getShardIterator());
        recordsRequest.setLimit(25);

        GetRecordsResult recordsResult = kinesis.getRecords(recordsRequest);
        while (!recordsResult.getRecords().isEmpty()) {
            recordsResult.getRecords()
                .stream()
                .map(record -> new String(record.getData()
                    .array()))
                .forEach(System.out::println);

            recordsRequest.setShardIterator(recordsResult.getNextShardIterator());
            recordsResult = kinesis.getRecords(recordsRequest);
        }
    }

    @PostConstruct
    private void buildShardIterator() {
        GetShardIteratorRequest readShardsRequest = new GetShardIteratorRequest();
        readShardsRequest.setStreamName(IPS_STREAM);
        readShardsRequest.setShardIteratorType(ShardIteratorType.LATEST);
        readShardsRequest.setShardId(IPS_SHARD_ID);
        this.shardIterator = kinesis.getShardIterator(readShardsRequest);
    }
}