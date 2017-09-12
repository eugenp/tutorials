package com.baeldung.beanvalidation.container;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;

public class StringContainer {
    
    private List<@NotNull String> container = new ArrayList<>();
    
    public void addElement(String element) {
        container.add(element);
    }
}
