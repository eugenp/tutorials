package com.baeldung.hexagonal.application;

import com.baeldung.hexagonal.domain.Documentation;

import java.util.Optional;

public interface ILoadDocumentation {
        Optional<Documentation> load(String className);
}
