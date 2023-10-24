package com.baeldung.connectiondetails;

import com.baeldung.connectiondetails.configuration.MongoDBConnectionDetailsConfiguration;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.r2dbc.R2dbcDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.r2dbc.R2dbcRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConnectionDetailsApplication.class)
@Import(MongoDBConnectionDetailsConfiguration.class)
@ComponentScan(basePackages = "com.baeldung.connectiondetails")
@TestPropertySource(locations = {"classpath:connectiondetails/application-mongo.properties"})
@ActiveProfiles("mongo")
public class MongoDBConnectionDetailsIntegrationTest {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Test
    public void givenSecretVault_whenExecuteQueryOnMongoDB_ReturnResult() throws JSONException {
        mongoTemplate.insert("{\"msg\":\"My First Entry in MongoDB\"}", "myDemoCollection");
        String result = mongoTemplate.find(new Query(), String.class, "myDemoCollection").get(0);

        JSONObject jsonObject = new JSONObject(result);
        result = jsonObject.get("msg").toString();
        R2dbcAutoConfiguration r2dbcAutoConfiguration;
        R2dbcDataAutoConfiguration r2dbcDataAutoConfiguration;
        R2dbcRepositoriesAutoConfiguration r2dbcRepositoriesAutoConfiguration;

        assertEquals("My First Entry in MongoDB", result);
    }
}

