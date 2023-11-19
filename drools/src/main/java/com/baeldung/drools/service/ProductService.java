package com.baeldung.drools.service;

import com.baeldung.drools.config.DroolsBeanFactory;
import com.baeldung.drools.model.Product;
import org.kie.api.runtime.KieSession;

public class ProductService {

    private KieSession kieSession = new DroolsBeanFactory().getKieSession();

    public Product applyLabelToProduct(Product product){
        try {
            kieSession.insert(product);
            kieSession.fireAllRules();
        } finally {
            kieSession.dispose();
        }
        System.out.println(product.getLabel());
        return product;

    }

}
