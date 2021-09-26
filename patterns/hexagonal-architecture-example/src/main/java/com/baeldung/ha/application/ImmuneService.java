package com.baeldung.ha.application;

import com.baeldung.ha.domain.Antibody;
import com.baeldung.ha.domain.Antigen;
import com.baeldung.ha.domain.InvalidAntigenException;

public interface ImmuneService {
    Antibody respond(Antigen antigen) throws InvalidAntigenException;
}
