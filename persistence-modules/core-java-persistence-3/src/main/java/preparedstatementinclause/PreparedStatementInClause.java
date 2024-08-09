package preparedstatementinclause;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class PreparedStatementInClause {

    public static ResultSet populateParamsWithStringBuilder(Connection connection, List<Integer> ids) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < ids.size(); i++) {
            stringBuilder.append("?,");
        }
        String placeHolders = stringBuilder.deleteCharAt(stringBuilder.length() - 1)
            .toString();
        String sql = "select * from customer where id in (" + placeHolders + ")";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 1; i <= ids.size(); i++) {
            preparedStatement.setInt(i, ids.get(i - 1));
        }
        return preparedStatement.executeQuery();
    }

    public static ResultSet populateParamsWithStream(Connection connection, List<Integer> ids) throws SQLException {

        var sql = String.format("select * from customer where id IN (%s)", ids.stream()
            .map(v -> "?")
            .collect(Collectors.joining(", ")));
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 1; i <= ids.size(); i++) {
            preparedStatement.setInt(i, ids.get(i - 1));
        }
        return preparedStatement.executeQuery();
    }

    public static ResultSet populateParamsWithArray(Connection connection, List<Integer> ids) throws SQLException {
        String sql = "SELECT * FROM customer where id IN (select * from table(x int = ?))";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        Array array = preparedStatement.getConnection()
            .createArrayOf("int", ids.toArray());
        preparedStatement.setArray(1, array);
        return preparedStatement.executeQuery();
    }

}
