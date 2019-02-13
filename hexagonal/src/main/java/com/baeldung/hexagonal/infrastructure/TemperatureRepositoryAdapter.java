package com.baeldung.hexagonal.infrastructure;

import com.baeldung.hexagonal.business.TemperatureRepository;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class TemperatureRepositoryAdapter implements TemperatureRepository {

        private final Map<String, Integer> cityToTemperature;

        public TemperatureRepositoryAdapter() throws IOException {
                cityToTemperature = initializeTemperatures();
        }

        public Optional<Integer> getTemperature(String city) {
                return Optional.ofNullable(cityToTemperature.get(city));
        }

        private Map<String, Integer> initializeTemperatures() {
                InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("temperatures.txt");
                Scanner scanner = new Scanner(resourceAsStream);

                Map<String, Integer> result = new HashMap<>();
                while (scanner.hasNext()) {
                        result.put(scanner.next(), scanner.nextInt());
                }

                return result;
        }
}
