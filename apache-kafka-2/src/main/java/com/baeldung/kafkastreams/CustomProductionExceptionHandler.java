package com.baeldung.kafkastreams;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.streams.errors.ErrorHandlerContext;
import org.apache.kafka.streams.errors.ProductionExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class CustomProductionExceptionHandler implements ProductionExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(UserSerializer.class);

    @Override
    public ProductionExceptionHandlerResponse handle(
            ErrorHandlerContext context,
            ProducerRecord<byte[], byte[]> record,
            Exception exception) {

        log.error("ProductionExceptionHandler Error producing record NodeId: {} | TaskId: {} | Topic: {} | Partition: {} | Exception: {}",
            context.processorNodeId(),
            context.taskId(),
            record.topic(),
            record.partition(),
            exception.getMessage()
        );

        return ProductionExceptionHandlerResponse.CONTINUE;
    }

    @Override
    public void configure(Map<String, ?> configs) {}
}