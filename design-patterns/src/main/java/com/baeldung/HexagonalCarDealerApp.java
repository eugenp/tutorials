package com.baeldung;

import com.baeldung.adapters.CarInMemoryRepositoryAdapter;
import com.baeldung.adapters.UserConsoleInterfaceAdapter;
import com.baeldung.domain.CarDealerService;
import com.baeldung.ports.CarRepositoryPort;
import com.baeldung.ports.UserInterfaceRequestPort;

public class HexagonalCarDealerApp {
    public static void main(String[] args) {
        CarRepositoryPort carRepositoryPort = new CarInMemoryRepositoryAdapter();
        UserInterfaceRequestPort requestPort = new CarDealerService(carRepositoryPort);
        UserConsoleInterfaceAdapter consoleAdapter = new UserConsoleInterfaceAdapter(requestPort);

        System.out.println("available cars:");
        printAllCars(carRepositoryPort);

        consoleAdapter.buyACar();

        System.out.println("cars left:");
        printAllCars(carRepositoryPort);
    }

    private static void printAllCars(CarRepositoryPort carRepositoryPort) {
        carRepositoryPort.getAll()
            .forEach(System.out::println);
    }
}
