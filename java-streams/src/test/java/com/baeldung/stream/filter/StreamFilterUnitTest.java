package com.baeldung.stream.filter;

import org.junit.jupiter.api.Test;
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
    public void givenListOfCustomers_whenFilterByLambda_thenGetTwo() {
        List<Customer> customers = Arrays.asList(new Customer("John P.", 15), new Customer("Sarah M.", 200), new Customer("Charles B.", 150), new Customer("Mary T.", 1));

        long customersWithMoreThan100Points = customers
          .stream()
          .filter(c -> c.getPoints() > 100)
          .count();

        assertThat(customersWithMoreThan100Points).isEqualTo(2);
    }

    @Test
    public void givenListOfCustomers_whenFilterByMethodReference_thenGetTwo() {
        List<Customer> customers = Arrays.asList(new Customer("John P.", 15), new Customer("Sarah M.", 200), new Customer("Charles B.", 150), new Customer("Mary T.", 1));

        long customersWithMoreThan100Points = customers
          .stream()
          .filter(Customer::hasOverThousandPoints)
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
          .filter(Customer::hasOverThousandPoints)
          .count();

        assertThat(customersWithMoreThan100Points).isEqualTo(2);
    }

    @Test
    public void givenListOfCustomers_whenFilterWithCustomHandling_thenThrowException() {
        List<Customer> customers = Arrays.asList(new Customer("John P.", 15, "https://images.unsplash.com/photo-1543320485-d0d5a49c2b2e"), new Customer("Sarah M.", 200), new Customer("Charles B.", 150),
          new Customer("Mary T.", 1, "https://images.unsplash.com/photo-1543297057-25167dfc180e"));

        assertThatThrownBy(() -> customers
          .stream()
          .filter(Customer::hasValidProfilePhotoWithoutCheckedException)
          .count()).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void givenListOfCustomers_whenFilterWithThrowingFunction_thenThrowException() {
        List<Customer> customers = Arrays.asList(new Customer("John P.", 15, "https://images.unsplash.com/photo-1543320485-d0d5a49c2b2e"), new Customer("Sarah M.", 200), new Customer("Charles B.", 150),
          new Customer("Mary T.", 1, "https://images.unsplash.com/photo-1543297057-25167dfc180e"));

        assertThatThrownBy(() -> customers
          .stream()
          .filter((ThrowingPredicate.unchecked(Customer::hasValidProfilePhoto)))
          .count()).isInstanceOf(WrappedException.class);
    }
}
