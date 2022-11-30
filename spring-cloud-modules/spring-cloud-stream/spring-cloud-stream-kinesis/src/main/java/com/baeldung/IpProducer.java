package com.baeldung;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.model.PutRecordsRequest;
import com.amazonaws.services.kinesis.model.PutRecordsRequestEntry;

@Component
@EnableBinding(Source.class)
public class IpProducer {

    @Value("${ips.partition.key}")
    private String IPS_PARTITION_KEY;
    @Value("${ips.stream}")
    private String IPS_STREAM;

    @Autowired
    private Source source;
    @Autowired
    private AmazonKinesis kinesis;

    @Scheduled(fixedDelay = 3000L)
    private void produce() {
        IntStream.range(1, 200).mapToObj(ipSuffix -> "192.168.0." + ipSuffix)
            .forEach(entry -> source.output().send(MessageBuilder.withPayload(entry).build()));
    }

    @Scheduled(fixedDelay = 3000L)
    private void produceWithKinesis() {
        List<PutRecordsRequestEntry> entries = IntStream.range(1, 200).mapToObj(ipSuffix -> {
            PutRecordsRequestEntry entry = new PutRecordsRequestEntry();
            entry.setData(ByteBuffer.wrap(("192.168.0." + ipSuffix).getBytes()));
            entry.setPartitionKey(IPS_PARTITION_KEY);
            return entry;
        }).collect(Collectors.toList());

        PutRecordsRequest createRecordsRequest = new PutRecordsRequest();
        createRecordsRequest.setStreamName(IPS_STREAM);
        createRecordsRequest.setRecords(entries);

        kinesis.putRecords(createRecordsRequest);
    }
}