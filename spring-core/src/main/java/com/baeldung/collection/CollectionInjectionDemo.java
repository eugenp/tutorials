package com.baeldung.collection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by Gebruiker on 5/18/2018.
 */
public class CollectionInjectionDemo {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(CollectionConfig.class);
        CollectionsBean collectionsBean = context.getBean(CollectionsBean.class);
        collectionsBean.printNameList();
        collectionsBean.printNameSet();
        collectionsBean.printNameMap();
        collectionsBean.printBeanList();
        collectionsBean.printNameListWithDefaults();
    }
}
