package com.baeldung.nullconversion;

import java.util.function.Consumer;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public class PersonProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        Person person = new Person();
        Address address = new Address();
        ZipCode zipCode = new ZipCode("98-323");
        address.setZipCode(zipCode);
        person.setAddress(address);

        return Stream.of(
          Arguments.of(person, Delivery.freeDelivery()),
          Arguments.of(cloneAndMutate(person, p -> p.getAddress().getZipCode().setCode("")), Delivery.defaultDelivery()),
          Arguments.of(cloneAndMutate(person, p -> p.getAddress().setZipCode(null)), Delivery.defaultDelivery()),
          Arguments.of(cloneAndMutate(person, p -> p.setAddress(null)), Delivery.defaultDelivery()),
          Arguments.of(null, Delivery.defaultDelivery())
        );

    }

    private static Person cloneAndMutate(Person person, Consumer<Person> mutator) {
        Person clone = person.clone();
        mutator.accept(clone);
        return clone;
    }
}
