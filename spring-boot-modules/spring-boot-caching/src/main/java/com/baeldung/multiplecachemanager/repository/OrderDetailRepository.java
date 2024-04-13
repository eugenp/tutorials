package com.baeldung.multiplecachemanager.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.baeldung.multiplecachemanager.entity.Item;
import com.baeldung.multiplecachemanager.entity.Order;

@Repository
public class OrderDetailRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Order getOrderDetail(Integer orderId) {
        String orderDetailQuery = "select * from orderdetail where orderid = ? ";
        Order order = new Order();
        jdbcTemplate.query(orderDetailQuery, new Object[] { orderId }, new RowCallbackHandler() {
            public void processRow(ResultSet rs) throws SQLException {
                order.setCustomerId(rs.getInt("customerid"));
                order.setOrderId(rs.getInt("orderid"));
                order.setItemId(rs.getInt("itemid"));
                order.setQuantity(rs.getInt("quantity"));
            }
        });
        return order;
    }

    public double getOrderPrice(Integer orderId) {

        String orderItemJoinQuery = "select * from ( select * from orderdetail where orderid = ? ) o left join item on o.itemid = ITEM.itemid";
        Order order = new Order();
        Item item = new Item();

        jdbcTemplate.query(orderItemJoinQuery, new Object[] { orderId }, new RowCallbackHandler() {
            public void processRow(ResultSet rs) throws SQLException {
                order.setCustomerId(rs.getInt("customerid"));
                order.setOrderId(rs.getInt("orderid"));
                order.setItemId(rs.getInt("itemid"));
                order.setQuantity(rs.getInt("quantity"));
                item.setItemDesc("itemdesc");
                item.setItemId(rs.getInt("itemid"));
                item.setItemPrice(rs.getDouble("price"));
            }
        });
        return order.getQuantity() * item.getItemPrice();
    }
}
