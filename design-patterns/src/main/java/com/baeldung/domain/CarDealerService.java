package com.baeldung.domain;

import com.baeldung.ports.CarRepositoryPort;
import com.baeldung.ports.UserInterfaceRequestPort;

public class CarDealerService implements UserInterfaceRequestPort {
    private final CarRepositoryPort repositoryPort;

    public CarDealerService(CarRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public void buy(String manufacturer, String model) {
        repositoryPort.get(manufacturer, model)
            .ifPresent(repositoryPort::remove);
    }
}
