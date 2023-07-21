package org.baeldung.mybatis_sqlscript;

import org.apache.ibatis.jdbc.ScriptRunner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;
import java.util.Scanner;


public class Main {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:mem:testDB";

    //  Database credentials
    static final String USER = "sa";
    static final String PASS = "";
    private static Connection conn;
    private static Statement stmt;
    final static Logger logger = Logger.getLogger("Main");
    public static void main(String[] args){
        try {
            //Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
        }
        catch(Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        }

        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter full path of the file: ");
            String filePath= sc.next();
            Path path = Paths.get(filePath);

            System.out.println("Press 1: Execute SQL Script Using Plain Java: ");
            System.out.println("Press 2: Execute SQL Script Using Apache iBatis: ");
            List<String> sqlLines=Files.readAllLines(path);
            int choice = sc.nextInt();

            switch (choice)
            {
                case 1:
                    // Running SQL Script using Plain Java
                    for (String sql : sqlLines) {
                        logger.info("Query: " + sql);

                        if (sql.contains("SELECT")) {
                            ResultSet rs = stmt.executeQuery(sql);
                            logger.info("ID" + "\t" + "Name" + "\t" + "Surname" + "\t" + "Age");
                            logger.info("");
                            // Extract data from result set
                            while (rs.next()) {
                                // Retrieve by column name
                                int id = rs.getRow();
                                int age = rs.getInt("age");
                                String name = rs.getString("name");
                                String surname = rs.getString("surname");

                                // Display values
                                logger.info(id + "\t" + name + "\t" + surname + "\t" + age);
                                logger.info("");
                            }
                        }
                        else
                            stmt.execute(sql);
                    }

                    break;

                case 2:
                    ScriptRunner scriptExecutor = new ScriptRunner(conn);
                    BufferedReader reader = new BufferedReader(new FileReader(filePath));
                    scriptExecutor.runScript(reader);
                    reader.close();
                    break;
            }
        }
        catch(SQLException se)
        {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        logger.info("Reached End of Code!");
    }

}






