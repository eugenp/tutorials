package com.baeldung.springsessionjdbc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SpringSessionJdbcIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Before
    public void setup() throws ClassNotFoundException {
        Class.forName("org.h2.Driver");
    }

    @Test
    public void givenApiHasStarted_whenH2DbIsQueried_thenSessionTablesAreEmpty() throws SQLException {
        Assert.assertEquals(0, getSessionIdsFromDatabase().size());
        Assert.assertEquals(0, getSessionAttributeBytesFromDatabase().size());
    }

    @Test
    public void givenGetInvoked_whenH2DbIsQueried_thenOneSessionIsCreated() throws SQLException {
        assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/", String.class)).isNotEmpty();
        Assert.assertEquals(1, getSessionIdsFromDatabase().size());
    }

    @Test
    public void givenPostInvoked_whenH2DbIsQueried_thenSessionAttributeIsRetrieved() throws ClassNotFoundException, SQLException, IOException {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("color", "red");
        this.testRestTemplate.postForObject("http://localhost:" + port + "/saveColor", map, String.class);
        List<byte[]> queryResponse = getSessionAttributeBytesFromDatabase();
        Assert.assertEquals(1, queryResponse.size());
        ObjectInput in = new ObjectInputStream(new ByteArrayInputStream(queryResponse.get(0)));
        List<String> obj = (List<String>) in.readObject(); //Deserialize byte[] to object
        Assert.assertEquals("red", obj.get(0));
    }

    private List<String> getSessionIdsFromDatabase() throws SQLException {
        List<String> result = new ArrayList<>();
        ResultSet rs = getResultSet("SELECT * FROM SPRING_SESSION");
        while (rs.next()) {
            result.add(rs.getString("SESSION_ID"));
        }
        return result;
    }

    private List<byte[]> getSessionAttributeBytesFromDatabase() throws SQLException {
        List<byte[]> result = new ArrayList<>();
        ResultSet rs = getResultSet("SELECT * FROM SPRING_SESSION_ATTRIBUTES");
        while (rs.next()) {
            result.add(rs.getBytes("ATTRIBUTE_BYTES"));
        }
        return result;
    }

    private ResultSet getResultSet(String sql) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
        Statement stat = conn.createStatement();
        return stat.executeQuery(sql);
    }

}
