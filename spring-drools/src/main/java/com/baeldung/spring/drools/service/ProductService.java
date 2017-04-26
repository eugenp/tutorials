package com.baeldung.spring.drools.service;

import com.baeldung.spring.drools.model.Product;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final KieContainer kieContainer;

    @Autowired
    public ProductService(KieContainer kieContainer){
        this.kieContainer = kieContainer;
    }

    public Product applyLabelToProduct(Product product){
        KieSession kieSession = kieContainer.newKieSession("ProductSession");
        kieSession.insert(product);
        kieSession.fireAllRules();
        System.out.println(product.getLabel());
        return  product;

    }

}
