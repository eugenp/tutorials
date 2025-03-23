package com.baeldung.jdbc.mocking.v2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import javax.sql.DataSource;

import com.baeldung.jdbc.mocking.Customer;

public class AllCustomers implements Supplier<List<Customer>> {

    private final DataSource dataSource;

    @Override
    public List<Customer> get() {
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM customers");
            List<Customer> customers = new ArrayList<>();
            while (resultSet.next()) {
                customers.add(mapCustomer(resultSet));
            }
            return customers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public AllCustomers(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Customer mapCustomer(ResultSet resultSet) throws SQLException {
        return new Customer(resultSet.getInt("id"), resultSet.getString("name"), Customer.Status.valueOf(resultSet.getString("status")));
    }

}