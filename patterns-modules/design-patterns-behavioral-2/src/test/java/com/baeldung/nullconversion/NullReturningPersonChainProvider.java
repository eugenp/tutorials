package com.baeldung.nullconversion;

import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public class NullReturningPersonChainProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        Person person = new Person();
        Address address = new Address();
        ZipCode zipCode = new ZipCode("98-323");
        address.setZipCode(zipCode);
        person.setAddress(address);

        return Stream.of(
          Arguments.of(PersonMutatorUtil.cloneAndMutate(person, p -> p.getAddress().getZipCode().setCode(""))),
          Arguments.of(PersonMutatorUtil.cloneAndMutate(person, p -> p.getAddress().setZipCode(null))),
          Arguments.of(PersonMutatorUtil.cloneAndMutate(person, p -> p.setAddress(null)))
        );

    }

}
