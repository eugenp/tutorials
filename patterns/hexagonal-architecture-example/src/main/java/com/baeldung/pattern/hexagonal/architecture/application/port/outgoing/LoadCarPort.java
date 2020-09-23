package com.baeldung.pattern.hexagonal.architecture.application.port.outgoing;

import com.baeldung.pattern.hexagonal.architecture.application.domain.Car;

import java.util.Optional;

public interface LoadCarPort {
    Optional<Car> load(Long id);
}
