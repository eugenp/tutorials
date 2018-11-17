package com.baeldung.hexagon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HolidayDBService implements HolidayService {

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultset = null;

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
        connection = DriverManager.getConnection("jdbc:hsqldb:mem://localhost/holidaydb", "SA", "");
        return connection;
    }

    @Override
    public boolean dateExists(String dateInStringFormat) {
        boolean dateFound = false;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            String query = "SELECT COUNT(ID) DATECOUNT FROM " + "HOLIDAYS WHERE DATE='" + dateInStringFormat + "'";
            resultset = statement.executeQuery(query);

            while (resultset.next()) {
                dateFound = resultset.getInt("DATECOUNT") > 0 ? true : false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // close connection here...
        }
        return dateFound;
    }
}
