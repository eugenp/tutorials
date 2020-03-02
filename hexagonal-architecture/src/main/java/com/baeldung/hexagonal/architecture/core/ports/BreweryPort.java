package com.baeldung.hexagonal.architecture.core.ports;

import java.io.IOException;
import java.util.List;

import com.baeldung.hexagonal.architecture.application.dtos.BreweryDto;

public interface BreweryPort {

    List<BreweryDto> search(String city) throws IOException, InterruptedException;
}
