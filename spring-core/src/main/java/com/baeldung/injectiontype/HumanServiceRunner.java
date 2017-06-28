package com.baeldung.injectiontype;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class HumanServiceRunner {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(HumanAppConfig.class);
        HumanService humanService = (HumanService) context.getBean(HumanService.class);
        humanService.performAction();
    }
}
