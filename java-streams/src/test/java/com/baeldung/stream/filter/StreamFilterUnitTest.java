package com.baeldung.stream.filter;

import org.junit.Test;
import pl.touk.throwing.ThrowingPredicate;
import pl.touk.throwing.exception.WrappedException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class StreamFilterUnitTest {

    @Test
    public void givenListOfCustomers_whenFilterBy100Points_thenGetTwo() {
        List<Customer> customers = Arrays.asList(new Customer("John P.", 15), new Customer("Sarah M.", 200), new Customer("Charles B.", 150), new Customer("Mary T.", 1));

        long customersWithMoreThan100Points = customers
          .stream()
          .filter(c -> c.hasOver(100))
          .count();

        assertThat(customersWithMoreThan100Points).isEqualTo(2);
    }

    @Test
    public void givenListOfCustomersWithOptional_whenFilterBy100Points_thenGetTwo() {
        List<Optional<Customer>> customers = Arrays.asList(Optional.of(new Customer("John P.", 15)), Optional.of(new Customer("Sarah M.", 200)), Optional.empty(), Optional.of(new Customer("Mary T.", 300)), Optional.empty());

        long customersWithMoreThan100Points = customers
          .stream()
          .flatMap(c -> c
            .map(Stream::of)
            .orElseGet(Stream::empty))
          .filter(c -> c.hasOver(100))
          .count();

        assertThat(customersWithMoreThan100Points).isEqualTo(2);
    }

    @org.junit.jupiter.api.Test
    public void givenListOfCustomers_whenFilterWithCustomHandling_thenThrowException() {
        List<Customer> customers = Arrays.asList(new Customer("John P.", 15, "https://images.pexels.com/photos/1624438/pexels-photo-1624438.jpeg"), new Customer("Sarah M.", 200), new Customer("Charles B.", 150),
          new Customer("Mary T.", 1, "https://images.pexels.com/photos/1181671/pexels-photo-1181671.jpeg"));

        assertThatThrownBy(() -> customers
          .stream()
          .filter(Customer::hasValidProfilePhotoWithoutCheckedException)
          .count()).isInstanceOf(RuntimeException.class);
    }

    @org.junit.jupiter.api.Test
    public void givenListOfCustomers_whenFilterWithThrowingFunction_thenThrowException() {
        List<Customer> customers = Arrays.asList(new Customer("John P.", 15, "https://images.pexels.com/photos/1624438/pexels-photo-1624438.jpeg"), new Customer("Sarah M.", 200), new Customer("Charles B.", 150),
          new Customer("Mary T.", 1, "https://images.pexels.com/photos/1181671/pexels-photo-1181671.jpeg"));

        assertThatThrownBy(() -> customers
          .stream()
          .filter(ThrowingPredicate.unchecked(Customer::hasValidProfilePhoto))
          .count()).isInstanceOf(WrappedException.class);
    }
}
