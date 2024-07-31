package io.orkes.example.saga.dao;

import io.orkes.example.saga.pojos.Customer;
import io.orkes.example.saga.pojos.Order;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.Date;

public class OrdersDAO extends BaseDAO {

    public OrdersDAO(String url) {
        super(url);
    }

    public String insertOrder(Order order) {
        Date date = new Date();
        Timestamp nowAsTS = new Timestamp(date.getTime());

        String itemsStr = String.join("", order.getOrderDetails().getItems().toString());

        String notesStr = null;

        if(!order.getOrderDetails().getNotes().isEmpty()) {
            notesStr = String.join("", order.getOrderDetails().getNotes().toString());
        } else {
            notesStr = "";
        }

        String sql = "INSERT INTO orders(orderId,customerId,restaurantId,deliveryAddress,createdAt,status) VALUES(?,?,?,?,?,?)";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, order.getOrderId());
            pstmt.setInt(2, order.getCustomer().getId());
            pstmt.setInt(3, order.getRestaurantId());
            pstmt.setString(4, order.getDeliveryAddress());
            pstmt.setTimestamp(5, nowAsTS);
            pstmt.setString(6, order.getStatus().name());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }

        sql = "INSERT INTO orders_details(orderId,items,notes) VALUES(?,?,?)";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, order.getOrderId());
            pstmt.setString(2, itemsStr);
            pstmt.setString(3, notesStr);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }

        return "";
    }

    public void updateOrder(Order order) {
        String sql = "UPDATE orders SET restaurantId=?,deliveryAddress=?,status=? WHERE orderId=?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, order.getRestaurantId());
            pstmt.setString(2, order.getDeliveryAddress());
            pstmt.setString(3, order.getStatus().name());
            pstmt.setString(4, order.getOrderId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void readOrder(String orderId, Order order) {
        String sql = "SELECT orderId, customerId, restaurantId, deliveryAddress, createdAt, status FROM orders WHERE orderId = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, orderId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                order.setOrderId(rs.getString("orderId"));
                Customer customer = new Customer();
                customer.setId(rs.getInt("customerId"));
                order.setCustomer(customer);
                order.setRestaurantId(rs.getInt("restaurantId"));
                order.setDeliveryAddress(rs.getString("deliveryAddress"));
                order.setCreatedAt(rs.getLong("createdAt"));
                order.setStatus(Order.Status.valueOf(rs.getString("status")));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int insertCustomer(Customer customer) {
        int id = 0;

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement("SELECT id FROM customers WHERE email = ?")) {
            pstmt.setString(1, customer.getEmail());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        if (id > 0) {
            return id;
        }

        String sql = "INSERT INTO customers(email,name,contact) VALUES(?,?,?)";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customer.getEmail());
            pstmt.setString(2, customer.getName());
            pstmt.setString(3, customer.getContact());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement("SELECT id FROM customers WHERE email = ?")) {
            pstmt.setString(1, customer.getEmail());
            ResultSet rs = pstmt.executeQuery();
            id = rs.getInt("id");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }
}
