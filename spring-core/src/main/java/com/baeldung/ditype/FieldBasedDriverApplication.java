package com.baeldung.ditype;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.ditype.config.Config;
import com.baeldung.ditype.file.processor.FieldBasedFileProcessor;

public class FieldBasedDriverApplication {

    public static void main(String[] args) {
        @SuppressWarnings("resource")
        FieldBasedFileProcessor fbfProcessor = new AnnotationConfigApplicationContext(Config.class).getBean(FieldBasedFileProcessor.class);
        fbfProcessor.process();
    }
}
