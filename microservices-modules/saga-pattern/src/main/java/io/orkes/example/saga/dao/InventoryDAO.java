package io.orkes.example.saga.dao;

import io.orkes.example.saga.pojos.Order;
import io.orkes.example.saga.pojos.Restaurant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InventoryDAO extends BaseDAO {

    public InventoryDAO(String url) {
        super(url);
    }

    public void readRestaurant(int restaurantId, Restaurant restaurant) {
        String sql = "SELECT name, address, contact FROM restaurants WHERE id = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, restaurantId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                restaurant.setName(rs.getString("name"));
                restaurant.setAddress(rs.getString("address"));
                restaurant.setContact(rs.getString("contact"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
