package com.baeldung.spring.drools.service;

import java.io.File;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

@Configurable
public class OrderServiceConfiguration {

    public static final String drlFile ="BAELDUNG_SERVICE_ORDER.drl";
    
    public KnowledgeBase knowledgeBase() {
        System.out.println("!!!!!knowledgeBase()!!!!!!");
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(drlFile).getFile());
        kbuilder.add(ResourceFactory.newFileResource(file), ResourceType.DRL);
        
        if (kbuilder.hasErrors()) {
            System.out.println(kbuilder.getErrors());
        }

        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
        return kbase;
    }

    @Bean
    public StatefulKnowledgeSession statefulKnowledgeSession() {
        StatefulKnowledgeSession ksession = knowledgeBase().newStatefulKnowledgeSession();
        return ksession;
    }
    
    @Bean
    public OrderService orderService(){
        OrderService orderService = new OrderService(statefulKnowledgeSession());
        return orderService;
    }

}
