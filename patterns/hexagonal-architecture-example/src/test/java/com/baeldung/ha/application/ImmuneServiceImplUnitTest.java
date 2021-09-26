package com.baeldung.ha.application;

import com.baeldung.ha.domain.Antibody;
import com.baeldung.ha.domain.Antigen;
import com.baeldung.ha.domain.InvalidAntigenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ImmuneServiceImplUnitTest {
    FakeImmuneStore store;
    ImmuneServiceImpl service;

    @BeforeEach
    void setUp() {
        this.store = new FakeImmuneStore();
        this.service = new ImmuneServiceImpl(store);
    }

    @Test
    void givenUnknownAntigens_whenRespond_thenReturnAntibody() throws InvalidAntigenException {
        final int size = 5;
        for (int i = 0; i < size; i++) {
            // Given a valid antigen
            Antigen antigen = new Antigen(i);

            // When calling respond
            Antibody antibody = service.respond(antigen);

            // Then antibody is returned
            assertNotNull(antibody);
            assertEquals(antigen, antibody.getAntigen());
            assertTrue(antibody.getEffort() > 0);
        }

        // Assert calls size
        assertEquals(size, store.findCounter);
        assertEquals(size, store.saveCounter);
    }
}
