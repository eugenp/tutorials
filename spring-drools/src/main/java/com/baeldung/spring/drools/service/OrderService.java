package com.baeldung.spring.drools.service;

import org.drools.runtime.StatefulKnowledgeSession;

import com.baeldung.spring.drools.model.Order;
import com.baeldung.spring.drools.model.Result;

public class OrderService {

    private StatefulKnowledgeSession ksessionBilling;

    public OrderService(StatefulKnowledgeSession statefulKnowledgeSession) {
        this.ksessionBilling = statefulKnowledgeSession;
    }

    public Long calculateBilling(Order order, Result result) {
        ksessionBilling.insert(order);
        ksessionBilling.setGlobal("result", result);
        ksessionBilling.fireAllRules();
        System.out.println("!! TOTAL SERVICE CHARGE : " + result.getTotalCharge());
        return result.getTotalCharge();
    }
}
