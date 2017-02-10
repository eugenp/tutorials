package com.baeldung.graph;

import org.junit.Test;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Danil Kornishev (danil.kornishev@mastercard.com)
 */
public class Neo4jOgmTest {

    @Test
    public void testOgm() {
        Configuration conf = new Configuration();
        conf.driverConfiguration().setDriverClassName("org.neo4j.ogm.drivers.embedded.driver.EmbeddedDriver");

        SessionFactory factory = new SessionFactory(conf, "com.baeldung.graph");
        Session session = factory.openSession();

        Car tesla = new Car("tesla", "modelS");
        Company baeldung = new Company("baeldung");

        baeldung.setCar(tesla);

        session.save(baeldung);

        Map<String, String> params = new HashMap<>();
        params.put("make", "tesla");
        Result result = session.query("MATCH (car:Car) <-[:owns]- (company:Company)" +
                " WHERE car.make=$make" +
                " RETURN company", params);

        Map<String, Object> firstResult = result.iterator().next();

        Assert.assertEquals(firstResult.size(), 1);

        Company actual = (Company) firstResult.get("company");
        Assert.assertEquals(actual.getName(), baeldung.getName());
    }
}
