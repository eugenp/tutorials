package com.baeldung.ha.application;

import com.baeldung.ha.domain.Antibody;
import com.baeldung.ha.domain.Antigen;
import com.baeldung.ha.domain.LymphaticSystem;
import com.baeldung.ha.domain.InvalidAntigenException;

public class ImmuneServiceImpl implements ImmuneService {
    private ImmuneStore store;

    public ImmuneServiceImpl(ImmuneStore store) {
        this.store = store;
    }

    @Override
    public Antibody respond(Antigen antigen) throws InvalidAntigenException {
        // Search for antibody in immune store or produce it
        Antibody antibody = store.find(antigen).orElse(LymphaticSystem.produce(antigen));

        // Save antibody
        store.save(antibody);

        return antibody;
    }
}
