package com.baeldung.hexagonarch.ports.driven;

import java.util.Collection;
import java.util.Optional;

public interface PersonRepository {
    Optional<Collection<Person>> findPersonsInNeighbourhood(String streetName, String city, String zipCode);
}
