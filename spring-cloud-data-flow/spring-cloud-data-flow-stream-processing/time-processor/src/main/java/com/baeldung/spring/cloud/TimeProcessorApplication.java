package com.baeldung.spring.cloud;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.Transformer;

@EnableBinding(Processor.class)
@SpringBootApplication
public class TimeProcessorApplication {

    @Transformer(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
    public Object transform(Long timestamp) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String date = dateFormat.format(timestamp);
        return date;
    }

    public static void main(String[] args) {
        SpringApplication.run(TimeProcessorApplication.class, args);
    }
}
