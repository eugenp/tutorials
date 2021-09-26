package com.baeldung.ha.application;

import com.baeldung.ha.domain.Antibody;
import com.baeldung.ha.domain.Antigen;

import java.util.Optional;

public interface ImmuneStore {
    void save(Antibody antibody);
    Optional<Antibody> find(Antigen antigen);
}
