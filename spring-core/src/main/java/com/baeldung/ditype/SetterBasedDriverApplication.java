package com.baeldung.ditype;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.ditype.config.Config;
import com.baeldung.ditype.file.processor.SetterBasedFileProcessor;

public class SetterBasedDriverApplication {

    public static void main(String[] args) {
        @SuppressWarnings("resource")
        SetterBasedFileProcessor sbfProcessor = new AnnotationConfigApplicationContext(Config.class).getBean(SetterBasedFileProcessor.class);
        sbfProcessor.process();
    }
}
