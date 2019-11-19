package com.baeldung.hexagonal.adapters.output;

import com.baeldung.hexagonal.spi.IPersistenceSpi;

public class RealPersistenceAdapter implements IPersistenceSpi {

        @Override public boolean persist() {
                System.out.println("Persisting data through real adapter.");

                return true;
        }
}
