package com.baeldung.jdbc.mocking;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.baeldung.jdbc.mocking.Customer.Status;

@ExtendWith(MockitoExtension.class)
class JdbcMockingUnitTest {

    @Mock
    DataSource ds;
    @Mock
    Connection conn;
    @Mock
    Statement stmt;
    @Mock
    ResultSet rs;

    @BeforeEach
    void setUp() throws Exception {
        when(ds.getConnection()).thenReturn(conn);
        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery("SELECT * FROM customers")).thenReturn(rs);
    }

    @Test
    void whenFetchingCustomersEligibleForOffer_thenTheyHaveActiveOrLoyalStatus() throws Exception {
        when(rs.next()).thenReturn(true, true, false);
        when(rs.getInt("id")).thenReturn(1, 2);
        when(rs.getString("name")).thenReturn("Alice", "Bob");
        when(rs.getString("status")).thenReturn("LOYAL", "ACTIVE");

        CustomersService customersService = new CustomersService(ds);

        List<Customer> customers = customersService.customersEligibleForOffers();

        assertThat(customers)
            .containsExactlyInAnyOrder(
                new Customer(1, "Alice", Status.LOYAL),
                new Customer(2, "Bob", Status.ACTIVE)
            );
    }

}