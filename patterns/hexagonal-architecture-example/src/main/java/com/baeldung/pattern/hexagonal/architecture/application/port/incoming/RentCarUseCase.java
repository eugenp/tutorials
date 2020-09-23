package com.baeldung.pattern.hexagonal.architecture.application.port.incoming;

public interface RentCarUseCase {
    boolean rent(Long id);
}
