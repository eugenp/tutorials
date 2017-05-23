package org.baeldung.bean.injectiontypes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class BeanInjectionRunner {
    private final static Logger logger = LoggerFactory.getLogger(BeanInjectionRunner.class);

    /**
     * Main method.
     */
    public static void main(String[] args) {

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(BeanInjectionConfig.class);

        SpringBeanInjectionWithConstructor constructorBased = ctx.getBean(SpringBeanInjectionWithConstructor.class);
        String dataFromConstructorService = constructorBased.getDataFromDelegate();
        logger.info("Data: " + dataFromConstructorService);


        SpringBeanInjectionWithSetter setterBased = ctx.getBean(SpringBeanInjectionWithSetter.class);
        String dataFromSetterService = setterBased.getDataFromDelegate();
        logger.info("Data: " + dataFromSetterService);

        assert dataFromConstructorService.equals(dataFromSetterService);


    }
}
