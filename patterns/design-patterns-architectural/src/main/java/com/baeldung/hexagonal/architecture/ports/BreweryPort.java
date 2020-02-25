package com.baeldung.hexagonal.architecture.ports;

import com.baeldung.hexagonal.architecture.core.Brewery;

import java.io.IOException;
import java.util.List;

public interface BreweryPort {
    List<Brewery> search(String city) throws IOException, InterruptedException;
}
