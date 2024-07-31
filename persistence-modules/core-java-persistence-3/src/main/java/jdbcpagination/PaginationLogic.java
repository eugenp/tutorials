package jdbcpagination;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaginationLogic {

    public static ResultSet readPageWithLimitAndOffset(Connection connection, int offset, int pageSize) throws SQLException {
        String sql = """
             SELECT * FROM employees
             LIMIT ? OFFSET ?
            """;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, pageSize);
        preparedStatement.setInt(2, offset);

        return preparedStatement.executeQuery();
    }

    public static ResultSet readPageWithSortedKeys(Connection connection, int lastFetchedId, int pageSize) throws SQLException {
        String sql = """
             SELECT * FROM employees
             WHERE id > ? LIMIT ?
            """;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, lastFetchedId);
        preparedStatement.setInt(2, pageSize);

        return preparedStatement.executeQuery();
    }
}
