package com.baeldung.streams.debug;

import com.baeldung.streams.debug.entity.Customer;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class Example2 {
    @Test
    public void whenDebugging_thenInformationIsShown() {
        List<Optional<Customer>> customers = Arrays.asList(
                Optional.of(new Customer("John P.", 15)),
                Optional.of(new Customer("Sarah M.", 78)),
                Optional.empty(),
                Optional.of(new Customer("Mary T.", 20)),
                Optional.empty(),
                Optional.of(new Customer("Florian G.", 89)),
                Optional.empty());

        long numberOf65PlusCustomers = customers
                .stream()
                .flatMap(c -> c
                        .map(Stream::of)
                        .orElseGet(Stream::empty))
                .mapToInt(Customer::getAge)
                .filter(c -> c > 65)
                .count();

        assertThat(numberOf65PlusCustomers).isEqualTo(2);
    }
}
