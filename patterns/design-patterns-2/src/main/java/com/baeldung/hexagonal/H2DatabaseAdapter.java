package com.baeldung.hexagonal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class H2DatabaseAdapter implements DataPort {

    private static final String DB_URL = "jdbc:h2:~/test", USER = "sa", PASS = "";

    public H2DatabaseAdapter() {
        init();
    }

    private void init() {

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement stmt = conn.createStatement()) {

            // start with a clean db
            stmt.execute("DROP ALL OBJECTS");

            String sql = "CREATE TABLE COFFEE_RECIPE " + "(ID INTEGER not NULL, " + " SUGER INTEGER, " + " COFFEE INTEGER)";
            stmt.execute(sql);

            // Insert data
            sql = "INSERT INTO COFFEE_RECIPE VALUES (1, 10, 50)";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO COFFEE_RECIPE VALUES (2, 7, 40)";
            stmt.executeUpdate(sql);

        } catch (SQLException se) {
            se.printStackTrace();
        }

    }

    @Override
    public CoffeeRecipe getCoffeeRecipeById(Integer id) {

        CoffeeRecipe coffeeRecipe = null;

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement stmt = conn.createStatement()) {

            String sql = "SELECT * FROM COFFEE_RECIPE where ID=" + id;
            ResultSet resultSet = stmt.executeQuery(sql);
            if (resultSet.next()) {
                coffeeRecipe = new CoffeeRecipe();
                coffeeRecipe.setId(resultSet.getInt("ID"));
                coffeeRecipe.setSugerInGrams(resultSet.getInt("SUGER"));
                coffeeRecipe.setCoffeeInGrams(resultSet.getInt("COFFEE"));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return coffeeRecipe;

    }

}