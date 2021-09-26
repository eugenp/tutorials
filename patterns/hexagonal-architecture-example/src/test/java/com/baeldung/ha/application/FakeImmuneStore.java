package com.baeldung.ha.application;

import com.baeldung.ha.domain.Antibody;
import com.baeldung.ha.domain.Antigen;

import java.util.Optional;

public class FakeImmuneStore implements ImmuneStore {
    int saveCounter = 0;
    int findCounter = 0;

    @Override
    public void save(Antibody antibody) {
        saveCounter++;
    }

    @Override
    public Optional<Antibody> find(Antigen antigen) {
        findCounter++;
        return Optional.empty();
    }
}
