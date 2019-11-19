package org.baeldung.spring.cloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@EnableBinding(Sink.class)
@SpringBootApplication
public class LogSinkApplication {

    private static Logger logger = LoggerFactory.getLogger(LogSinkApplication.class);

    @StreamListener(Sink.INPUT)
    public void loggerSink(String date) {

        logger.info("Received: " + date);
    }

    public static void main(String[] args) {
        SpringApplication.run(LogSinkApplication.class, args);
    }
}
