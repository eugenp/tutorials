package com.baeldung.hexagonal_architecture;

import java.sql.*;

public class DbAmountService implements IAmountService {
    Connection con;

    public DbAmountService() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "root", "root");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getAmount(String city) {
        try (Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT amount FROM cities WHERE name = '" + city + "'");
            return rs.getInt("amount");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
