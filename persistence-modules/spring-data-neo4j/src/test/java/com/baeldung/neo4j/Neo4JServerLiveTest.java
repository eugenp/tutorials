package com.baeldung.neo4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

@Ignore
public class Neo4JServerLiveTest {

    @Test
    public void standAloneDriver() {
        Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "12345"));
        Session session = driver.session();

        session.run("CREATE (baeldung:Company {name:\"Baeldung\"}) " +
                "-[:owns]-> (tesla:Car {make: 'tesla', model: 'modelX'})" +
                "RETURN baeldung, tesla");

        StatementResult result = session.run("MATCH (company:Company)-[:owns]-> (car:Car)" +
                "WHERE car.make='tesla' and car.model='modelX'" +
                "RETURN company.name");

        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(result.next().get("company.name").asString(), "Baeldung");

        session.close();
        driver.close();
    }
    
    @Test
    public void standAloneJdbc() throws Exception {
        Connection con = DriverManager.getConnection("jdbc:neo4j:bolt://localhost/?user=neo4j,password=12345,scheme=basic");

        // Querying
        try (Statement stmt = con.createStatement()) {
            stmt.execute("CREATE (baeldung:Company {name:\"Baeldung\"}) " +
                    "-[:owns]-> (tesla:Car {make: 'tesla', model: 'modelX'})" +
                    "RETURN baeldung, tesla");

            ResultSet rs = stmt.executeQuery("MATCH (company:Company)-[:owns]-> (car:Car)" +
                    "WHERE car.make='tesla' and car.model='modelX'" +
                    "RETURN company.name");

            while (rs.next()) {
                Assert.assertEquals(rs.getString("company.name"), "Baeldung");
            }
        }
        con.close();
    }
}
