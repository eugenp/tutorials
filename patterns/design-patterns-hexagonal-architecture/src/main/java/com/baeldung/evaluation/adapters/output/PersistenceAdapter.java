package com.baeldung.evaluation.adapters.output;

import com.baeldung.evaluation.hexagon.spi.IPersistenceSpi;

public class PersistenceAdapter implements IPersistenceSpi {
    public boolean persist() {
        System.out.println("Persisting data through real adapter.");

        return true;
    }
}
