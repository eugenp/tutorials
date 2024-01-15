package jdbcpagination;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaginationLogic {

    public static List<List<String>> readAllPaginatedWithLimitAndOffset(Connection connection, int pageSize) throws SQLException {
        int offset = 0;  // offset is set to 0 and keep updating with pageSize
        int batchNum = 1; // batchNum to keep track of num of batches processed
        String sql = """
             SELECT * FROM employees
             LIMIT ? OFFSET ?
            """;
        List<List<String>> result = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        long execTime = 0;
        List<String> currBatch = new ArrayList<>();
        while (true) {
            preparedStatement.setInt(1, pageSize);
            preparedStatement.setInt(2, offset);
            long startTime = System.currentTimeMillis();
            ResultSet resultSet = preparedStatement.executeQuery();
            execTime += System.currentTimeMillis() - startTime;
            if (!resultSet.next())
                break; // break when we consumed all the data
            do {
                currBatch.add(resultSet.getString("first_name"));
            } while (resultSet.next());
            batchNum++;
            offset += pageSize; // shift offset to pageSize
            result.add(currBatch);
        }
        System.out.println("total records read: " + result.stream()
            .flatMap(List::stream)
            .toList()
            .size());
        System.out.println("exec time" + execTime);

        return result;
    }

    public static List<List<String>> readAllPaginatedWithSortedKeys(Connection connection, int pageSize) throws SQLException {
        // find min and max ID
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT min(id) as min_id, max(id) as max_id FROM employees");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int minId = resultSet.getInt("min_id");
        int maxId = resultSet.getInt("max_id");
        int lastFetchedId = minId; // assign lastFetchedId to minId

        List<List<String>> result = new ArrayList<>();
        // query table using where clause that filter over id range.
        String sql = """
             SELECT * FROM employees
             WHERE id > ? and id <= ?
            """;
        preparedStatement = connection.prepareStatement(sql);
        long execTime = 0;

        while ((lastFetchedId + pageSize) <= maxId) { // keep iterating over windows of ID's until reached to maxId
            preparedStatement.setInt(1, lastFetchedId);
            preparedStatement.setInt(2, lastFetchedId + pageSize);
            long startTime = System.currentTimeMillis();
            resultSet = preparedStatement.executeQuery();
            long endTime = System.currentTimeMillis();
            execTime += endTime - startTime;
            List<String> currBatch = new ArrayList<>();
            while (resultSet.next()) {
                currBatch.add(resultSet.getString("first_name"));
                lastFetchedId = resultSet.getInt("id");
            }
            result.add(currBatch);
        }

        // reading last page since we may have missed it above if last page is lesser than pageSize
        if (maxId - lastFetchedId > 0) {
            preparedStatement.setInt(1, lastFetchedId);
            preparedStatement.setInt(2, maxId);
            long startTime = System.currentTimeMillis();
            resultSet = preparedStatement.executeQuery();
            execTime += System.currentTimeMillis() - startTime;
            List<String> currBatch = new ArrayList<>();
            while (resultSet.next()) {
                currBatch.add(resultSet.getString("first_name"));
                lastFetchedId = resultSet.getInt("id");
            }
            result.add(currBatch);
        }

        System.out.println("max_id: %s, last_fetched_id: %s".formatted(maxId, lastFetchedId));
        System.out.println("total exec time: %s".formatted(execTime));

        return result;
    }
}
