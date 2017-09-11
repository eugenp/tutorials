package com.baeldung.beanvalidation.container;

import java.util.Optional;
import javax.validation.constraints.Positive;

public class IntegerContainer {

    private Optional<@Positive(message = "Value must be a positive integer") Integer> container = Optional.empty();
    
    public void addElement(int element) {
        container = Optional.of(element);
    }
}
