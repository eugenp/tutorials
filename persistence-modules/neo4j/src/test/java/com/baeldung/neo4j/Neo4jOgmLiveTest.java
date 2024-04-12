package com.baeldung.neo4j;

import static com.baeldung.neo4j.TestContainersTestBase.getDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

import com.baeldung.neo4j.domain.Car;
import com.baeldung.neo4j.domain.Company;

/**
 * To run this test you will need to have an instance of the docker running on your machine (Docker desktop - for Windows and Docker instance for linux)
 * After your docker instance is up run this test
 */
public class Neo4jOgmLiveTest {

    private static SessionFactory sessionFactory;
    private static Session session;

    @BeforeAll
    public static void oneTimeSetUp() {
        sessionFactory = new SessionFactory(getDriver(), "com.baeldung.neo4j.domain");
        session = sessionFactory.openSession();
        session.purgeDatabase();
    }

    @Test
    void testOgm() {
        Car tesla = new Car("tesla", "modelS");
        Company baeldung = new Company("baeldung");

        baeldung.setCar(tesla);

        session.save(baeldung);

        assertEquals(1, session.countEntitiesOfType(Company.class));

        Map<String, String> params = new HashMap<>();
        params.put("make", "tesla");
        Result result = session.query("MATCH (car:Car) <-[:owns]- (company:Company)" +
                " WHERE car.make=$make" +
                " RETURN company", params);

        Map<String, Object> firstResult = result.iterator().next();

        assertEquals(1, firstResult.size());

        Company actual = (Company) firstResult.get("company");
        assertEquals(actual.getName(), baeldung.getName());
    }
}
