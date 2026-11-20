package com.baeldung.kafkastreams;

import org.apache.kafka.streams.errors.ErrorHandlerContext;
import org.apache.kafka.streams.errors.ProcessingExceptionHandler;
import org.apache.kafka.streams.processor.api.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class CustomProcessingExceptionHandler implements ProcessingExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(CustomProcessingExceptionHandler.class);

    @Override
    public ProcessingHandlerResponse handle(ErrorHandlerContext errorHandlerContext, Record<?, ?> record, Exception ex) {
        log.error("ProcessingExceptionHandler Error for record NodeId: {} | TaskId: {} | Key: {} | Value: {} | Exception: {}",
            errorHandlerContext.processorNodeId(), errorHandlerContext.taskId(), record.key(), record.value(), ex.getMessage(), ex);

        return ProcessingHandlerResponse.CONTINUE;
    }

    @Override
    public void configure(Map<String, ?> configs) {
    }
}