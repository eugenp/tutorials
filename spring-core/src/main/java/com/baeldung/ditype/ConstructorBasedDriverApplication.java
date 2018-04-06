package com.baeldung.ditype;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.ditype.config.Config;
import com.baeldung.ditype.file.processor.ConstructorBasedFileProcessor;

public class ConstructorBasedDriverApplication {
    public static void main(String[] args) {
        @SuppressWarnings("resource")
        ConstructorBasedFileProcessor cbfProcessor = new AnnotationConfigApplicationContext(Config.class).getBean(ConstructorBasedFileProcessor.class);
        cbfProcessor.process();
    }
}
