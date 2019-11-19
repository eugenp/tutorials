package com.baeldung.evaluation.adapters.output;

import com.baeldung.evaluation.hexagon.spi.IPersistenceSpi;

public class MockPersistenceAdapter implements IPersistenceSpi {

        @Override public boolean persist() {
                System.out.println("Persisting data through mocked adapter.");
                return true;
        }
}
