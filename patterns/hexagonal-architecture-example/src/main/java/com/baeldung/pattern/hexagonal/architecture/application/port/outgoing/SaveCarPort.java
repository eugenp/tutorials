package com.baeldung.pattern.hexagonal.architecture.application.port.outgoing;

import com.baeldung.pattern.hexagonal.architecture.application.domain.Car;

public interface SaveCarPort {
    void save(Car car);
}
