package com.baeldung.di.example.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(App.class);
        
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        Store store = context.getBean("store", Store.class);

        logger.info(store.getItemDescription());

        ((AnnotationConfigApplicationContext) context).close();
    }

}
