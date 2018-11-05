package com.baeldung.hexagonal_architecture;

import java.sql.*;

public class MyDbLogic {
    Connection con;

    public MyDbLogic() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/database", "root", "root");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int addToAmount(int toAdd, String city) {
        try (Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT amount FROM cities WHERE name = '" + city + "'");
            return rs.getInt("amount") + toAdd;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}