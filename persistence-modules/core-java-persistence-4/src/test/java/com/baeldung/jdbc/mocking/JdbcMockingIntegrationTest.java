package com.baeldung.jdbc.mocking;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.util.List;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.baeldung.jdbc.mocking.Customer.Status;

class JdbcMockingIntegrationTest {

    private static JdbcDataSource dataSource;

    @BeforeAll
    static void setUp() {
        dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'classpath:data.sql'");
        dataSource.setUser("sa");
        dataSource.setPassword("");
    }

    @Test
    void whenFetchingCustomersEligibleForOffers_thenTheyHaveActiveOrLoyalStatus() {
        CustomersService customersService = new CustomersService(dataSource);

        List<Customer> customers = customersService.customersEligibleForOffers();

        assertThat(customers)
            .extracting(Customer::status)
            .containsOnly(Status.ACTIVE, Status.LOYAL);
    }

}