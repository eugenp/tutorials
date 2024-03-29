package com.baeldung.multiplecachemanager.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.baeldung.multiplecachemanager.entity.Customer;
import com.baeldung.multiplecachemanager.entity.Order;

@Repository
public class CustomerDetailRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Customer getCustomerDetail(Integer customerId) {
        String customerQuery = "select * from customer where customerid = ? ";
        Customer customer = new Customer();
        jdbcTemplate.query(customerQuery, new Object[] { customerId }, new RowCallbackHandler() {
            public void processRow(ResultSet rs) throws SQLException {
                customer.setCustomerId(rs.getInt("customerid"));
                customer.setCustomerName(rs.getString("customername"));
            }
        });
        return customer;
    }

    public List<Order> getCustomerOrders(Integer customerId) {
        String customerOrderQuery = "select * from orderdetail where customerid = ? ";
        List<Order> orders = new ArrayList<Order>();
        jdbcTemplate.query(customerOrderQuery, new Object[] { customerId }, new RowCallbackHandler() {
            public void processRow(ResultSet rs) throws SQLException {
                Order order = new Order();
                order.setCustomerId(rs.getInt("customerid"));
                order.setItemId(rs.getInt("orderid"));
                order.setOrderId(rs.getInt("orderid"));
                order.setQuantity(rs.getInt("quantity"));
                orders.add(order);
            }
        });
        return orders;
    }
}
