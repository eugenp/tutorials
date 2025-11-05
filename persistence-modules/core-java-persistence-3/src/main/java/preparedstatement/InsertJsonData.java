package preparedstatement;

import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertJsonData {

    private static final String URL = "jdbc:postgresql://localhost:5432/database_name";
    private static final String USER = "username";
    private static final String PASSWORD = "password";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


    public void insertUser(String name, JSONObject info) throws SQLException {
        String sql = "INSERT INTO users (name, info) VALUES (?, ?::jsonb)";

        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, name);
        pstmt.setString(2, info.toString());
        pstmt.executeUpdate();

        System.out.println("Data inserted successfully.");
    }

    public static void main(String[] args) throws SQLException {
        JSONObject jsonInfo = new JSONObject();
        jsonInfo.put("email", "john.doe@example.com");
        jsonInfo.put("age", 30);
        jsonInfo.put("active", true);

        InsertJsonData insertJsonData = new InsertJsonData();
        insertJsonData.insertUser("John Doe", jsonInfo);
    }
}