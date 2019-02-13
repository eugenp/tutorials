package com.baeldung.hexagonal.business;

import java.util.Optional;

public class DefaultWeatherQuery implements WeatherQuery {

        private final TemperatureRepository temperatureRepository;

        public DefaultWeatherQuery(TemperatureRepository temperatureRepository) {
                this.temperatureRepository = temperatureRepository;
        }

        public WeatherStatus query(String city) {
                Optional<Integer> temperatureOptional = temperatureRepository.getTemperature(city);
                if (!temperatureOptional.isPresent()) {
                        return WeatherStatus.UNKNOWN;
                }

                Integer temperature = temperatureOptional.get();
                if (temperature < 10) {
                        return WeatherStatus.COLD;
                } else if (temperature >= 10 && temperature < 20) {
                        return WeatherStatus.WARM;
                } else {
                        return WeatherStatus.HOT;
                }
        }
}
