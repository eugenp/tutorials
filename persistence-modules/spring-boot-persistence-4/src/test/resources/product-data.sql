CREATE ALIAS SHA256_HEX AS '
    import java.sql.*;
    @CODE
    String getSha256Hex(Connection conn, String value) throws SQLException {
        var sql = "SELECT RAWTOHEX(HASH(''SHA-256'', ?))";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, value);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        }
        return null;
    }
';

INSERT INTO product(name) VALUES('Hand Grip Strengthener');