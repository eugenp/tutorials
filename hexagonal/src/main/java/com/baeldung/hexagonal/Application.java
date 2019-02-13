package com.baeldung.hexagonal;

import com.baeldung.hexagonal.business.DefaultWeatherQuery;
import com.baeldung.hexagonal.business.TemperatureRepository;
import com.baeldung.hexagonal.business.WeatherQuery;
import com.baeldung.hexagonal.infrastructure.TemperatureRepositoryAdapter;
import com.baeldung.hexagonal.userinterface.ConsoleAdapter;
import java.io.IOException;

public class Application {

        public static void main(String[] args) throws IOException {
                TemperatureRepository temperatureRepository = new TemperatureRepositoryAdapter();

                WeatherQuery weatherQuery = new DefaultWeatherQuery(temperatureRepository);

                ConsoleAdapter consoleAdapter = new ConsoleAdapter(weatherQuery);

                consoleAdapter.start();
        }
}
