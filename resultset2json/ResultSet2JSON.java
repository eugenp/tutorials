///usr/bin/env jbang "$0" "$@" ; exit $?

//JAVA 17
//DEPS org.duckdb:duckdb_jdbc:0.3.2
//DEPS org.jooq:jooq:3.16.4
//DEPS org.json:json:20211205
//DEPS junit:junit:4.13.2

//FILES ../example.csv=./example.csv

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.IntStream;

import org.jooq.RecordMapper;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.jooq.tools.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

public class ResultSet2JSON { // https://stackoverflow.com/questions/6514876/most-efficient-conversion-of-resultset-to-json

    public static void main(String... args) throws ClassNotFoundException, SQLException {
        
        var testClass = new ResultSet2JSON();

        testClass.testMethod1();
        //testClass.testMethod2();
        //testClass.testMethod2();
        


    }

    @Test
    public void testMethod1() throws ClassNotFoundException, SQLException {
        Class.forName("org.duckdb.DuckDBDriver");

        var dbConnection = DriverManager.getConnection("jdbc:duckdb:");
        // create a table
        var stmt = dbConnection.createStatement();
        stmt.execute("CREATE TABLE words AS SELECT * FROM './example.csv'"); // https://duckdb.org/docs/data/csv
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM words");
        
        

        var result1 = resultSet2JdbcMethod1(resultSet);
        System.out.println(result1.toString());

        
        resultSet.close();


    }

    @Test
    public void testMethod2() throws ClassNotFoundException, SQLException {
        Class.forName("org.duckdb.DuckDBDriver");

        var dbConnection = DriverManager.getConnection("jdbc:duckdb:");
        // create a table
        var stmt = dbConnection.createStatement();
        stmt.execute("CREATE TABLE words AS SELECT * FROM './example.csv'"); // https://duckdb.org/docs/data/csv
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM words");
        
        

        var result1 = resultSet2JdbcMethod2(resultSet, dbConnection);
        System.out.println(result1.toString());

        
        resultSet.close();


    }

    @Test
    public void testMethod3() throws ClassNotFoundException, SQLException {
        Class.forName("org.duckdb.DuckDBDriver");

        var dbConnection = DriverManager.getConnection("jdbc:duckdb:");
        // create a table
        var stmt = dbConnection.createStatement();
        stmt.execute("CREATE TABLE words AS SELECT * FROM './example.csv'"); // https://duckdb.org/docs/data/csv
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM words");
        
        

        var result1 = resultSet2JdbcMethod3(resultSet, dbConnection);
        System.out.println(result1.toString());

        
        resultSet.close();


    }

    private static JSONArray resultSet2JdbcMethod1(ResultSet resultSet) throws SQLException
    {
        var md = resultSet.getMetaData();
        var numCols = md.getColumnCount();
        List<String> colNames = IntStream.range(0, numCols).mapToObj(i -> {
            try {
                return md.getColumnName(i+1);
            } catch (SQLException e) {
                
                e.printStackTrace();
                return "?";
            }
        }).toList();
        
        var result = new JSONArray();
        while (resultSet.next())
        {
            var row = new JSONObject();
            colNames.forEach(cn -> {
                try {
                    row.put(cn, resultSet.getObject(cn) );
                } catch (JSONException | SQLException e) {
                    
                    e.printStackTrace();
                }
            });
            result.add(row);

        }
        return result;
    }

    private static JSONObject resultSet2JdbcMethod2(ResultSet resultSet, Connection dbConnection) throws SQLException 
    {
        return new JSONObject(DSL.using(dbConnection).fetch(resultSet).formatJSON());
    }

    private static JSONArray resultSet2JdbcMethod3(ResultSet resultSet, Connection dbConnection) throws SQLException 
    {
        var md = resultSet.getMetaData();
        var numCols = md.getColumnCount();
        List<String> colNames = IntStream.range(0, numCols).mapToObj(i -> {
            try {
                return md.getColumnName(i+1);
            } catch (SQLException e) {
                
                e.printStackTrace();
                return "?";
            }
        }).toList();

        var json = DSL.using(dbConnection).fetch(resultSet).map(new RecordMapper<Record,JSONObject>() {

            @Override
            public JSONObject map(Record r) {
                var obj = new JSONObject();
                colNames.forEach(cn -> obj.put(cn, r.get(cn)));
                return obj;
            }
            
        } );
        return new JSONArray(json);
    }

}
