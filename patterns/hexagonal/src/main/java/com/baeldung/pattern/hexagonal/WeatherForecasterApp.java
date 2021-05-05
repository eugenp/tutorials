package com.baeldung.pattern.hexagonal;

import com.baeldung.pattern.hexagonal.repository.RepositoryFactory;
import com.baeldung.pattern.hexagonal.repository.WeatherRepository;
import com.baeldung.pattern.hexagonal.service.WeatherForecasterService;
import com.baeldung.pattern.hexagonal.ui.WeatherForecasterConsoleUI;
import com.baeldung.pattern.hexagonal.ui.WeatherForecasterUI;

public class WeatherForecasterApp {
    public static void main(String[] args) {
        WeatherRepository repository = RepositoryFactory.getMockWeatherRepository();
        WeatherForecasterService service = new WeatherForecasterService(repository);
        WeatherForecasterUI ui = new WeatherForecasterConsoleUI(service);

        ui.start();
    }
}
