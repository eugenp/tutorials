package com.baeldung.hexagonal.adapters.output;

import com.baeldung.hexagonal.spi.IPersistenceSpi;

public class MockPersistenceAdapter implements IPersistenceSpi {

        @Override public boolean persist() {
                System.out.println("Persisting data through mocked adapter.");
                return true;
        }
}
