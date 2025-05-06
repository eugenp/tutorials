package com.baeldung.jdbc.mocking;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.baeldung.jdbc.mocking.Customer.Status;
import com.baeldung.jdbc.mocking.v2.CustomersServiceV2;

@ExtendWith(MockitoExtension.class)
class JdbcMockingUnitTest {

    @Mock
    DataSource dataSource;
    @Mock
    Connection conn;
    @Mock
    Statement stmt;
    @Mock
    ResultSet resultSet;

    @Test
    void whenFetchingEligibleCustomers_thenTheyHaveCorrectStatus() throws Exception {
        //given
        CustomersService customersService = new CustomersService(dataSource);

        when(dataSource.getConnection())
          .thenReturn(conn);
        when(conn.createStatement())
          .thenReturn(stmt);
        when(stmt.executeQuery("SELECT * FROM customers"))
           .thenReturn(resultSet);

        when(resultSet.next())
          .thenReturn(true, true, true, false);
        when(resultSet.getInt("id"))
          .thenReturn(1, 2, 3);
        when(resultSet.getString("name"))
          .thenReturn("Alice", "Bob", "John");
        when(resultSet.getString("status"))
          .thenReturn("LOYAL", "ACTIVE", "INACTIVE");

        // when
        List<Customer> eligibleCustomers = customersService.customersEligibleForOffers();

        // then
        assertThat(eligibleCustomers).containsExactlyInAnyOrder(new Customer(1, "Alice", Status.LOYAL), new Customer(2, "Bob", Status.ACTIVE));
    }

    @Test
    void whenFetchingEligibleCustomersFromV2_thenTheyHaveCorrectStatus() {
        // given
        List<Customer> allCustomers = List.of(new Customer(1, "Alice", Status.LOYAL), new Customer(2, "Bob", Status.ACTIVE),
            new Customer(3, "John", Status.INACTIVE));

        CustomersServiceV2 service = new CustomersServiceV2(() -> allCustomers);

        // when
        List<Customer> eligibleCustomers = service.customersEligibleForOffers();

        // then
        assertThat(eligibleCustomers).containsExactlyInAnyOrder(new Customer(1, "Alice", Status.LOYAL), new Customer(2, "Bob", Status.ACTIVE));
    }

}