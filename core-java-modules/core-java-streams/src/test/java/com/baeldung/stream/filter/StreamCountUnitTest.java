package com.baeldung.stream.filter;

import org.junit.Test;
import org.junit.Before;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StreamCountUnitTest {

    private List<Customer> customers;

    @Before
    public void setUp() {
        Customer john = new Customer("John P.", 15, "https://images.unsplash.com/photo-1543320485-d0d5a49c2b2e");
        Customer sarah = new Customer("Sarah M.", 200);
        Customer charles = new Customer("Charles B.", 150);
        Customer mary = new Customer("Mary T.", 1, "https://images.unsplash.com/photo-1543297057-25167dfc180e");
        customers = Arrays.asList(john, sarah, charles, mary);
    }

    @Test
    public void givenListOfCustomers_whenCount_thenGetListSize() {
        long count = customers
          .stream()
          .count();

        assertThat(count).isEqualTo(4L);
    }

    @Test
    public void givenListOfCustomers_whenFilterByPointsOver100AndCount_thenGetTwo() {
        long countBigCustomers = customers
          .stream()
          .filter(c -> c.getPoints() > 100)
          .count();

        assertThat(countBigCustomers).isEqualTo(2L);
    }

    @Test
    public void givenListOfCustomers_whenFilterByPointsAndNameAndCount_thenGetOne() {
        long count = customers
          .stream()
          .filter(c -> c.getPoints() > 10 && c.getName().startsWith("Charles"))
          .count();

        assertThat(count).isEqualTo(1L);
    }

    @Test
    public void givenListOfCustomers_whenNoneMatchesFilterAndCount_thenGetZero() {
        long count = customers
          .stream()
          .filter(c -> c.getPoints() > 500)
          .count();

        assertThat(count).isEqualTo(0L);
    }

    @Test
    public void givenListOfCustomers_whenUsingMethodOverHundredPointsAndCount_thenGetTwo() {
        long count = customers
          .stream()
          .filter(Customer::hasOverHundredPoints)
          .count();

        assertThat(count).isEqualTo(2L);
    }
}
