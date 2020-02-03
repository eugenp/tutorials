package com.baeldung.hexagonalarchitecture.infrastructure;

import com.baeldung.hexagonalarchitecture.domain.Flight;
import com.baeldung.hexagonalarchitecture.domain.FlightsProvider;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FlightsFileProvider implements FlightsProvider {
    @Override
    public List<Flight> findFlights(String origin, String destination) throws IOException {
        String fileName = String.format("data/%s-%s.dat", origin, destination);
        File resource = new ClassPathResource(fileName).getFile();
        return Files.readAllLines(resource.toPath())
            .stream()
            .map(entry -> entry.split(","))
            .map(entry -> new Flight(entry[0], Double.parseDouble(entry[1])))
            .collect(Collectors.toList());
    }
}
