package com.baeldung.evaluation.adapters.output;

import com.baeldung.evaluation.hexagon.spi.IPersistenceSpi;

public class RealPersistenceAdapter implements IPersistenceSpi {

        @Override public boolean persist() {
                System.out.println("Persisting data through real adapter.");

                return true;
        }
}
