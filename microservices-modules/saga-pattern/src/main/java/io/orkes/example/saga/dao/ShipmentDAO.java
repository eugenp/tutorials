package io.orkes.example.saga.dao;

import io.orkes.example.saga.pojos.Driver;
import io.orkes.example.saga.pojos.Shipment;

import java.sql.*;
import java.util.Date;

public class ShipmentDAO extends BaseDAO {

    public ShipmentDAO(String url) {
        super(url);
    }

    public boolean insertShipment(Shipment shipment) {
        Date date = new Date();
        Timestamp nowAsTS = new Timestamp(date.getTime());

        String sql = "INSERT INTO shipments(orderId,driverId,address,instructions,createdAt,status) VALUES(?,?,?,?,?,?)";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, shipment.getOrderId());
            pstmt.setInt(2, shipment.getDriverId());
            pstmt.setString(3, shipment.getDeliveryAddress());
            pstmt.setString(4, shipment.getDeliveryInstructions());
            pstmt.setTimestamp(5, nowAsTS);
            pstmt.setString(6, Shipment.Status.SCHEDULED.name());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    public void cancelShipment(String orderId) {
        String sql = "UPDATE shipments SET status=? WHERE orderId=?;";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Shipment.Status.CANCELED.name());
            pstmt.setString(2, orderId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void confirmShipment(String orderId) {
        String sql = "UPDATE shipments SET status=? WHERE orderId=?;";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Shipment.Status.CONFIRMED.name());
            pstmt.setString(2, orderId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void readDriver(int driverId, Driver driver) {
        String sql = "SELECT name, contact FROM drivers WHERE id = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, driverId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                driver.setId(driverId);
                driver.setName(rs.getString("name"));
                driver.setContact(rs.getString("contact"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
