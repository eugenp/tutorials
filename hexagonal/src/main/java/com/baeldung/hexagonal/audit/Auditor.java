package com.baeldung.hexagonal.audit;

import com.baeldung.hexagonal.model.Item;
import org.springframework.stereotype.Component;

@Component
public class Auditor {
    public void audit(Item retrieved) {
        //do something
    }
}
