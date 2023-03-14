package com.baeldung.camel.boot.exception;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ExceptionLoggingProcessor implements Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionLoggingProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        Map<String, Object> headersMap = exchange.getIn().getHeaders();

        if (!headersMap.isEmpty()) {
            headersMap.entrySet()
                .stream()
                .forEach(e -> LOGGER.info("Header key [{}] -||- Header value [{}]", e.getKey(), e.getValue()));
        } else {
            LOGGER.info("Empty header");
        }

    }

}
