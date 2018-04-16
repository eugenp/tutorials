package com.baeldung.dependson;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.dependson.config.Config;
import com.baeldung.dependson.file.processor.FileProcessor;

public class DriverApplication {
    public static void main(String[] args) {
        @SuppressWarnings("resource")
        FileProcessor processor = new AnnotationConfigApplicationContext(Config.class).getBean(FileProcessor.class);
        processor.process();
    }
}
