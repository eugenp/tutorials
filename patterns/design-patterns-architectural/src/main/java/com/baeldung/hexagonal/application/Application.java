package com.baeldung.hexagonal.application;

import com.baeldung.hexagonal.port.PriceCalculator;

public class Application {
    private final PriceCalculator primaryAdapter;

    public Application(PriceCalculator primaryAdapter) {
        super();
        this.primaryAdapter = primaryAdapter;
    }

    public PriceCalculator getPrimaryAdapter() {
        return primaryAdapter;
    }

   
}
