package io.orkes.example.saga.dao;

import java.sql.*;

public class BaseDAO {

    private String url;

    public BaseDAO(String url) {
        this.url = url;
    }

    protected Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(this.url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    protected Boolean execute(String sql) {
        try (Connection conn = DriverManager.getConnection(this.url); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public void createTables(String service) {
        switch (service) {
            case "orders":
                createOrdersTable();
                createOrderDetailsTable();
                createCustomerTable();
                break;
            case "inventory":
                createRestaurantsTable();
                break;
            case "shipments":
                createDriversTable();
                createShipmentTable();
                break;
            case "payments":
                createPaymentsTable();
                break;
            default:
                System.out.println("Service name not recognized");
        }
    }

    private void createOrdersTable() {
        if (!tableExists("orders")) {

            String sql = "CREATE TABLE orders (\n"
                    + "	orderId text PRIMARY KEY,\n"
                    + "	customerId integer NOT NULL,\n"
                    + "	restaurantId integer NOT NULL,\n"
                    + "	deliveryAddress text NOT NULL,\n"
                    + "	createdAt TIMESTAMP NOT NULL,\n"
                    + "	status text NOT NULL\n"
                    + ");";

            execute(sql);
        }
    }

    private void createOrderDetailsTable() {
        if (!tableExists("orders_details")) {
            String sql = "CREATE TABLE orders_details (\n"
                    + "	orderId text PRIMARY KEY,\n"
                    + "	items text NOT NULL,\n"
                    + "	notes text\n"
                    + ");";

            execute(sql);
        }
    }

    private void createCustomerTable() {
        if (tableExists("customers")) {
            return;
        }

        String sql = "CREATE TABLE customers (\n"
                + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "	email text NOT NULL,\n"
                + "	name text NOT NULL,\n"
                + "	contact text\n"
                + ");";

        if(execute(sql)) {
            seedCustomers();
        }
    }

    private void createRestaurantsTable() {
        if (!tableExists("restaurants")) {
            String sql = "CREATE TABLE restaurants (\n"
                    + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                    + "	name text NOT NULL,\n"
                    + "	address text NOT NULL,\n"
                    + "	contact text NOT NULL\n"
                    + ");";

            if (execute(sql)) {
                seedRestaurants();
            }
        }
    }


    private void createPaymentsTable() {
        if (tableExists("payments")) {
            return;
        }

        String sql = "CREATE TABLE payments (\n"
                + "	paymentId text PRIMARY KEY,\n"
                + "	orderId text NOT NULL,\n"
                + "	amount number NOT NULL,\n"
                + "	method text,\n"
                + "	status text,\n"
                + "	createdAt TIMESTAMP NOT NULL\n"
                + ");";

        execute(sql);
    }

    private void createDriversTable() {
        if (tableExists("drivers")) {
            return;
        }

        String sql = "CREATE TABLE drivers (\n"
                + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "	name text NOT NULL,\n"
                + "	contact text\n"
                + ");";

        if(execute(sql)) {
            seedDrivers();
        }
    }
    private void createShipmentTable() {
        if (tableExists("shipments")) {
            return;
        }

        String sql = "CREATE TABLE shipments (\n"
                + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "	orderId text NOT NULL,\n"
                + "	driverId number NOT NULL,\n"
                + "	address text NOT NULL,\n"
                + "	instructions text,\n"
                + "	status text NOT NULL,\n"
                + "	createdAt TIMESTAMP NOT NULL\n"
                + ");";

        execute(sql);
    }

    private void seedCustomers() {
        String[] queries = {
                "INSERT INTO customers(email, name, contact) VALUES('John Smith','john.smith@example.com','+12126781345');",
                "INSERT INTO customers(email, name, contact) VALUES('Mike Ross','mike.ross@example.com','+15466711147');",
                "INSERT INTO customers(email, name, contact) VALUES('Martha Williams','martha.williams@example.com','+12790581941');"
        };

        for (String query : queries) {
            execute(query);
        }
    }

    private void seedRestaurants() {
        String[] add = {
                "5331 Redford Court, Montgomery AL 36116",
                "43 West 4th Street, New York NY 10024",
                "1693 Alice Court, Annapolis MD 21401"
        };
        String[] queries = {
                "INSERT INTO restaurants(name, address, contact) VALUES('Mikes','+12121231345','" + add[0] + "');",
                "INSERT INTO restaurants(name, address, contact) VALUES('Tamarind','+12412311147','" + add[1] + "');",
                "INSERT INTO restaurants(name, address, contact) VALUES('Thai Place','+14790981941','" + add[2] + "');",
        };

        for (String query : queries) {
            execute(query);
        }
    }

    private void seedDrivers() {
        String[] queries = {
                "INSERT INTO drivers(name,contact) VALUES('Wayne Stevens','+12520711467');",
                "INSERT INTO drivers(name,contact) VALUES('Jim Willis','+16466281981');",
                "INSERT INTO drivers(name,contact) VALUES('Steven Carroll','+12612590430');",
                "INSERT INTO drivers(name,contact) VALUES('Tom Cruise','+18659581430');"
        };

        for (String query : queries) {
            execute(query);
        }
    }

    boolean tableExists(String tableName) {
        try {
            Connection conn = DriverManager.getConnection(this.url);
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet resultSet = meta.getTables(null, null, tableName, new String[] {"TABLE"});
            boolean exists = resultSet.next();
            conn.close();
            return exists;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
