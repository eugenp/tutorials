package io.orkes.example.saga.dao;

import io.orkes.example.saga.pojos.Payment;

import java.sql.*;
import java.util.Date;

public class PaymentsDAO extends BaseDAO {

    public PaymentsDAO(String url) {
        super(url);
    }

    public String insertPayment(Payment payment) {
        Date date = new Date();
        Timestamp nowAsTS = new Timestamp(date.getTime());

        String sql = "INSERT INTO payments(paymentId, orderId, amount, method, createdAt, status) VALUES(?,?,?,?,?,?);";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, payment.getPaymentId());
            pstmt.setString(2, payment.getOrderId());
            pstmt.setDouble(3, payment.getAmount());
            pstmt.setString(4, payment.getPaymentMethod().toString());
            pstmt.setTimestamp(5, nowAsTS);
            pstmt.setString(6, payment.getStatus().name());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
        return "";
    }

    public void updatePaymentStatus(Payment payment) {
        String sql = "UPDATE payments SET status=? WHERE paymentId=?;";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, payment.getStatus().name());
            pstmt.setString(2, payment.getPaymentId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void readPayment(String orderId, Payment payment) {
        String sql = "SELECT paymentId, orderId, amount, method, createdAt, status FROM payments WHERE orderId = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, orderId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                payment.setPaymentId(rs.getString("paymentId"));
                payment.setOrderId(rs.getString("orderId"));
                payment.setAmount(rs.getDouble("amount"));
                payment.setCreatedAt(rs.getLong("createdAt"));
                payment.setStatus(Payment.Status.valueOf(rs.getString("status")));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
