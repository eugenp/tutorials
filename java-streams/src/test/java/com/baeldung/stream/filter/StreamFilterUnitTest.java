package com.baeldung.stream.filter;

import org.junit.jupiter.api.Test;
import pl.touk.throwing.ThrowingPredicate;
import pl.touk.throwing.exception.WrappedException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class StreamFilterUnitTest {

    @Test
    public void givenListOfCustomers_whenFilterByPoints_thenGetTwo() {
        Customer john = new Customer("John P.", 15);
        Customer sarah = new Customer("Sarah M.", 200);
        Customer charles = new Customer("Charles B.", 150);
        Customer mary = new Customer("Mary T.", 1);
        List<Customer> customers = Arrays.asList(john, sarah, charles, mary);

        List<Customer> customersWithMoreThan100Points = customers
          .stream()
          .filter(c -> c.getPoints() > 100)
          .collect(Collectors.toList());

        assertThat(customersWithMoreThan100Points).hasSize(2);
        assertThat(customersWithMoreThan100Points).contains(sarah, charles);
    }

    @Test
    public void givenListOfCustomers_whenFilterByPointsAndName_thenGetOne() {
        Customer john = new Customer("John P.", 15);
        Customer sarah = new Customer("Sarah M.", 200);
        Customer charles = new Customer("Charles B.", 150);
        Customer mary = new Customer("Mary T.", 1);
        List<Customer> customers = Arrays.asList(john, sarah, charles, mary);

        List<Customer> charlesWithMoreThan100Points = customers
          .stream()
          .filter(c -> c.getPoints() > 100 && c
            .getName()
            .startsWith("Charles"))
          .collect(Collectors.toList());

        assertThat(charlesWithMoreThan100Points).hasSize(1);
        assertThat(charlesWithMoreThan100Points).contains(charles);
    }

    @Test
    public void givenListOfCustomers_whenFilterByMethodReference_thenGetTwo() {
        Customer john = new Customer("John P.", 15);
        Customer sarah = new Customer("Sarah M.", 200);
        Customer charles = new Customer("Charles B.", 150);
        Customer mary = new Customer("Mary T.", 1);
        List<Customer> customers = Arrays.asList(john, sarah, charles, mary);

        List<Customer> customersWithMoreThan100Points = customers
          .stream()
          .filter(Customer::hasOverHundredPoints)
          .collect(Collectors.toList());

        assertThat(customersWithMoreThan100Points).hasSize(2);
        assertThat(customersWithMoreThan100Points).contains(sarah, charles);
    }

    @Test
    public void givenListOfCustomersWithOptional_whenFilterBy100Points_thenGetTwo() {
        Optional<Customer> john = Optional.of(new Customer("John P.", 15));
        Optional<Customer> sarah = Optional.of(new Customer("Sarah M.", 200));
        Optional<Customer> mary = Optional.of(new Customer("Mary T.", 300));
        List<Optional<Customer>> customers = Arrays.asList(john, sarah, Optional.empty(), mary, Optional.empty());

        List<Customer> customersWithMoreThan100Points = customers
          .stream()
          .flatMap(c -> c
            .map(Stream::of)
            .orElseGet(Stream::empty))
          .filter(Customer::hasOverHundredPoints)
          .collect(Collectors.toList());

        assertThat(customersWithMoreThan100Points).hasSize(2);
        assertThat(customersWithMoreThan100Points).contains(sarah.get(), mary.get());
    }

    @Test
    public void givenListOfCustomers_whenFilterWithCustomHandling_thenThrowException() {
        Customer john = new Customer("John P.", 15, "https://images.unsplash.com/photo-1543320485-d0d5a49c2b2e");
        Customer sarah = new Customer("Sarah M.", 200);
        Customer charles = new Customer("Charles B.", 150);
        Customer mary = new Customer("Mary T.", 1, "https://images.unsplash.com/photo-1543297057-25167dfc180e");
        List<Customer> customers = Arrays.asList(john, sarah, charles, mary);

        assertThatThrownBy(() -> customers
          .stream()
          .filter(Customer::hasValidProfilePhotoWithoutCheckedException)
          .count()).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void givenListOfCustomers_whenFilterWithThrowingFunction_thenThrowException() {
        Customer john = new Customer("John P.", 15, "https://images.unsplash.com/photo-1543320485-d0d5a49c2b2e");
        Customer sarah = new Customer("Sarah M.", 200);
        Customer charles = new Customer("Charles B.", 150);
        Customer mary = new Customer("Mary T.", 1, "https://images.unsplash.com/photo-1543297057-25167dfc180e");
        List<Customer> customers = Arrays.asList(john, sarah, charles, mary);

        assertThatThrownBy(() -> customers
          .stream()
          .filter((ThrowingPredicate.unchecked(Customer::hasValidProfilePhoto)))
          .collect(Collectors.toList())).isInstanceOf(WrappedException.class);
    }

    @Test
    public void givenListOfCustomers_whenFilterWithTryCatch_thenGetTwo() {
        Customer john = new Customer("John P.", 15, "https://images.unsplash.com/photo-1543320485-d0d5a49c2b2e");
        Customer sarah = new Customer("Sarah M.", 200);
        Customer charles = new Customer("Charles B.", 150);
        Customer mary = new Customer("Mary T.", 1, "https://images.unsplash.com/photo-1543297057-25167dfc180e");
        List<Customer> customers = Arrays.asList(john, sarah, charles, mary);

        List<Customer> customersWithValidProfilePhoto = customers
          .stream()
          .filter(c -> {
              try {
                  return c.hasValidProfilePhoto();
              } catch (IOException e) {
                  //handle exception
              }
              return false;
          })
          .collect(Collectors.toList());

        assertThat(customersWithValidProfilePhoto).hasSize(2);
        assertThat(customersWithValidProfilePhoto).contains(john, mary);
    }

    @Test
    public void givenListOfCustomers_whenFilterWithTryCatchAndRuntime_thenThrowException() {
        List<Customer> customers = Arrays.asList(new Customer("John P.", 15, "https://images.unsplash.com/photo-1543320485-d0d5a49c2b2e"), new Customer("Sarah M.", 200), new Customer("Charles B.", 150),
          new Customer("Mary T.", 1, "https://images.unsplash.com/photo-1543297057-25167dfc180e"));

        assertThatThrownBy(() -> customers
          .stream()
          .filter(c -> {
              try {
                  return c.hasValidProfilePhoto();
              } catch (IOException e) {
                  throw new RuntimeException(e);
              }
          })
          .collect(Collectors.toList())).isInstanceOf(RuntimeException.class);
    }
    
}
