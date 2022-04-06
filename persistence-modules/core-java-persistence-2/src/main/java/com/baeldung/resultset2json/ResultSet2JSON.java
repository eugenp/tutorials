package com.baeldung.resultset2json;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.impl.DSL;
import org.jooq.tools.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResultSet2JSON {

    public static void main(String... args) throws ClassNotFoundException, SQLException {

        ResultSet2JSON testClass = new ResultSet2JSON();
        testClass.testMethod1();
    }

    public void testMethod1() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        Connection dbConnection = DriverManager.getConnection("jdbc:h2:mem:rs2jdbc", "user", "password");

        // Create a table
        Statement stmt = dbConnection.createStatement();
        stmt.execute("CREATE TABLE words AS SELECT * FROM CSVREAD('./example.csv')");
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM words");

        JSONArray result1 = resultSet2JdbcMethod1(resultSet);
        System.out.println(result1.toString());

        resultSet.close();
    }

    public void testMethod2() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        Connection dbConnection = DriverManager.getConnection("jdbc:h2:mem:rs2jdbc", "user", "password");
        // Create a table
        Statement stmt = dbConnection.createStatement();
        stmt.execute("CREATE TABLE words AS SELECT * FROM CSVREAD('./example.csv')"); // See
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM words");

        JSONObject result1 = resultSet2JdbcMethod2(resultSet, dbConnection);
        System.out.println(result1.toString());

        resultSet.close();
    }

    public void testMethod3() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        Connection dbConnection = DriverManager.getConnection("jdbc:h2:mem:rs2jdbc", "user", "password");
        // Create a table
        Statement stmt = dbConnection.createStatement();
        stmt.execute("CREATE TABLE words AS SELECT * FROM CSVREAD('./example.csv')");
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM words");

        JSONArray result1 = resultSet2JdbcMethod3(resultSet, dbConnection);
        System.out.println(result1.toString());

        resultSet.close();
    }

    private static JSONArray resultSet2JdbcMethod1(ResultSet resultSet) throws SQLException {
        ResultSetMetaData md = resultSet.getMetaData();
        int numCols = md.getColumnCount();
        List<String> colNames = IntStream.range(0, numCols)
            .mapToObj(i -> {
                try {
                    return md.getColumnName(i + 1);
                } catch (SQLException e) {

                    e.printStackTrace();
                    return "?";
                }
            })
            .collect(Collectors.toList());

        JSONArray result = new JSONArray();
        while (resultSet.next()) {
            JSONObject row = new JSONObject();
            colNames.forEach(cn -> {
                try {
                    row.put(cn, resultSet.getObject(cn));
                } catch (JSONException | SQLException e) {

                    e.printStackTrace();
                }
            });
            result.add(row);
        }
        return result;
    }

    private static JSONObject resultSet2JdbcMethod2(ResultSet resultSet, Connection dbConnection) throws SQLException {
        return new JSONObject(DSL.using(dbConnection)
            .fetch(resultSet)
            .formatJSON());
    }

    private static JSONArray resultSet2JdbcMethod3(ResultSet resultSet, Connection dbConnection) throws SQLException {
        ResultSetMetaData md = resultSet.getMetaData();
        int numCols = md.getColumnCount();
        List<String> colNames = IntStream.range(0, numCols)
            .mapToObj(i -> {
                try {
                    return md.getColumnName(i + 1);
                } catch (SQLException e) {

                    e.printStackTrace();
                    return "?";
                }
            })
            .collect(Collectors.toList());

        List<JSONObject> json = DSL.using(dbConnection)
            .fetch(resultSet)
            .map(new RecordMapper<Record, JSONObject>() {

                @Override
                public JSONObject map(Record r) {
                    JSONObject obj = new JSONObject();
                    colNames.forEach(cn -> obj.put(cn, r.get(cn)));
                    return obj;
                }
            });
        return new JSONArray(json);
    }
}
