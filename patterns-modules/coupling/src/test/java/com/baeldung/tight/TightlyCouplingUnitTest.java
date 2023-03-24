package com.baeldung.tight;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

class TightlyCouplingUnitTest {

    @Test
    public void givenMetadataCollector_thenCollectMetadata() {
        MetadataCollector collector = mock(MetadataCollector.class);
        doNothing().when(collector)
            .collectMetadata();
    }

    @Test
    public void givenMetadataCollectorWithDifferentInput_thenCollectMetadata() {
        MetadataCollector collector = new MetadataCollector();
        collector.collectMetadata(1, 1);
    }
}