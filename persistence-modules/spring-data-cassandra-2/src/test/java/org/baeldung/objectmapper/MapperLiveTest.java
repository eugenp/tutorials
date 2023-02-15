package org.baeldung.objectmapper;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import org.baeldung.objectmapper.dao.CounterDao;
import org.baeldung.objectmapper.dao.UserDao;
import org.baeldung.objectmapper.entity.Counter;
import org.baeldung.objectmapper.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

@Testcontainers
@SpringBootTest
public class MapperLiveTest {

    private static final String KEYSPACE_NAME = "baeldung";

    @Container
    private static final CassandraContainer cassandra = (CassandraContainer) new CassandraContainer("cassandra:3.11.2")
            .withExposedPorts(9042);

    static void setupCassandraConnectionProperties() {
        System.setProperty("spring.data.cassandra.keyspace-name", KEYSPACE_NAME);
        System.setProperty("spring.data.cassandra.contact-points", cassandra.getContainerIpAddress());
        System.setProperty("spring.data.cassandra.port", String.valueOf(cassandra.getMappedPort(9042)));
    }

    static UserDao userDao;
    static CounterDao counterDao;

    @BeforeAll
    static void setup() {
        setupCassandraConnectionProperties();
        CqlSession session = CqlSession.builder().build();

        String createKeyspace = "CREATE KEYSPACE IF NOT EXISTS baeldung " +
                "WITH replication = {'class':'SimpleStrategy', 'replication_factor':1};";
        String useKeyspace = "USE baeldung;";
        String createUserTable = "CREATE TABLE IF NOT EXISTS user_profile " +
                "(id int, username text, user_age int, writetime bigint, PRIMARY KEY (id, user_age)) " +
                "WITH CLUSTERING ORDER BY (user_age DESC);";
        String createAdminTable = "CREATE TABLE IF NOT EXISTS admin_profile " +
                "(id int, username text, user_age int, role text, writetime bigint, department text, " +
                "PRIMARY KEY (id, user_age)) " +
                "WITH CLUSTERING ORDER BY (user_age DESC);";
        String createCounter = "CREATE TABLE IF NOT EXISTS counter " +
                "(id text, count counter, PRIMARY KEY (id));";

        session.execute(createKeyspace);
        session.execute(useKeyspace);
        session.execute(createUserTable);
        session.execute(createAdminTable);
        session.execute(createCounter);

        DaoMapper mapper = new DaoMapperBuilder(session).build();
        userDao = mapper.getUserDao(CqlIdentifier.fromCql("baeldung"));
        counterDao = mapper.getUserCounterDao(CqlIdentifier.fromCql("baeldung"));
    }

    @Test
    void givenUser_whenInsert_thenRetrievedDuringGet() {
        User user = new User(1, "JohnDoe", 31);
        userDao.insertUser(user);
        User retrievedUser = userDao.getUserById(1);
        Assertions.assertEquals(retrievedUser.getUserName(), user.getUserName());
    }

    @Test
    void givenCounter_whenIncrement_thenIncremented() {
        Counter users = counterDao.getCounterById("users");
        long initialCount = users != null ? users.getCount(): 0;

        counterDao.incrementCounter("users", 1);

        users = counterDao.getCounterById("users");
        long finalCount = users != null ? users.getCount(): 0;

        Assertions.assertEquals(finalCount - initialCount, 1);
    }

    @Test
    void givenUser_whenGetUsersOlderThan_thenRetrieved() {
        User user = new User(2, "JaneDoe", 20);
        userDao.insertUser(user);
        List<User> retrievedUsers = userDao.getUsersOlderThanAge(30).all();
        Assertions.assertEquals(retrievedUsers.size(), 1);
    }

}