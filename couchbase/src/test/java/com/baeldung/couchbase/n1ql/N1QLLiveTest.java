package com.baeldung.couchbase.n1ql;

import static com.baeldung.couchbase.n1ql.CodeSnippets.extractJsonResult;
import static com.couchbase.client.java.query.Select.select;
import static com.couchbase.client.java.query.dsl.Expression.i;
import static com.couchbase.client.java.query.dsl.Expression.s;
import static com.couchbase.client.java.query.dsl.Expression.x;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import com.couchbase.client.java.query.Statement;
import com.fasterxml.jackson.databind.JsonNode;

import rx.Observable;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { IntegrationTestConfig.class })
public class N1QLLiveTest {


    @Autowired
    private Cluster cluster;

    @Autowired
    private BucketFactory bucketFactory;

    @Test
    public void givenAutowiredCluster_whenNotNull_thenNotNull() {
        assertNotNull(cluster);
    }

    @Test
    public void givenBucketFactory_whenGetTestBucket_thenNotNull() {
        assertNotNull(bucketFactory.getTestBucket());
    }

    @Test
    public void givenBucketFactory_whenGetTravelSampleBucket_thenNotNull() {
        assertNotNull(bucketFactory.getTravelSampleBucket());
    }

    @Test
    public void givenDocument_whenInsert_thenResult() {
        Bucket bucket = bucketFactory.getTestBucket();
        JsonObject personObj = JsonObject.create()
                .put("name", "John")
                .put("email", "john@doe.com")
                .put("interests", JsonArray.from("Java", "Nigerian Jollof"));

        String id = UUID.randomUUID().toString();
        JsonDocument doc = JsonDocument.create(id, personObj);
        bucket.insert(doc);
        assertNotNull(bucket.get(id));
    }

    @Test
    public void whenBasicSelectQuery_thenGetQueryResult() {
        Bucket bucket = bucketFactory.getTravelSampleBucket();
        N1qlQueryResult result
                = bucket.query(N1qlQuery.simple("SELECT * FROM test"));

        result.forEach(System.out::println);

        System.out.println("result count: " + result.info().resultCount());
        System.out.println("error count: " + result.info().errorCount());
    }

    @Test
    public void givenSelectStatement_whenQuery_thenResult() {
        Bucket bucket = bucketFactory.getTravelSampleBucket();
        String query = "SELECT name FROM `travel-sample` " +
                "WHERE type = 'airport' LIMIT 100";
        N1qlQueryResult result1 = bucket.query(N1qlQuery.simple(query));

        System.out.println("Result Count " + result1.info().resultCount());

        N1qlQueryRow row = result1.allRows().get(0);
        JsonObject rowJson = row.value();
        System.out.println("Name in First Row " + rowJson.get("name"));

    }

    @Test
    public void givenSelectStatement2_whenQuery_thenResult() {
        Bucket bucket = bucketFactory.getTravelSampleBucket();
        JsonObject pVal = JsonObject.create().put("type", "airport");
        String query = "SELECT * FROM `travel-sample` " +
                "WHERE type = $type LIMIT 100";
        N1qlQueryResult r2 = bucket.query(N1qlQuery.parameterized(query, pVal));

        System.out.println(r2.allRows());

        List<JsonNode> list = extractJsonResult(r2);
        System.out.println(
                list.get(0).get("travel-sample").get("airportname").asText());
    }

    @Test
    public void givenSelectDSL_whenQuery_thenResult() {
        Bucket bucket = bucketFactory.getTravelSampleBucket();
        Statement statement = select("*")
                .from(i("travel-sample"))
                .where(x("type").eq(s("airport")))
                .limit(100);
        N1qlQueryResult r3 = bucket.query(N1qlQuery.simple(statement));

        List<JsonNode> list2 = extractJsonResult(r3);
        System.out.println("First Airport Name: " + list2.get(0).get("travel-sample").get("airportname").asText());

    }

    @Test
    public void givenSelectStatementWithOperators_whenQuery_thenResult() {
        Bucket bucket = bucketFactory.getTravelSampleBucket();
        String query2 = "SELECT t.city, " +
                "t.airportname || \" (\" || t.faa || \")\" AS portname_faa " +
                "FROM `travel-sample` t " +
                "WHERE t.type=\"airport\"" +
                "AND t.country LIKE '%States'" +
                "AND t.geo.lat >= 70 " +
                "LIMIT 2";
        N1qlQueryResult r4 = bucket.query(N1qlQuery.simple(query2));
        List<JsonNode> list3 = extractJsonResult(r4);
        System.out.println("First Doc : " + list3.get(0));
    }

    @Test
    public void givenSelectStatementWithDSL2_whenQuery_thenResult() {
        Bucket bucket = bucketFactory.getTravelSampleBucket();
        Statement st2 = select(
                x("t.city, t.airportname")
                        .concat(s(" (")).concat(x("t.faa")).concat(s(")")).as("portname_faa"))
                .from(i("travel-sample").as("t"))
                .where( x("t.type").eq(s("airport"))
                        .and(x("t.country").like(s("%States")))
                        .and(x("t.geo.lat").gte(70)))
                .limit(2);
        N1qlQueryResult r5 = bucket.query(N1qlQuery.simple(st2));
        List<JsonNode> list5 = extractJsonResult(r5);
        System.out.println("First Doc : " + list5.get(0));
        System.out.println("Query from Statement2: " + st2.toString());
    }

    @Test
    public void givenInsertStatement_whenQuery_thenUpdate() {
        Bucket bucket = bucketFactory.getTravelSampleBucket();
        String query = "INSERT INTO `travel-sample` (KEY, VALUE) " +
                " VALUES(" +
                "\"cust1293\", " +
                "{\"id\":\"1293\",\"name\":\"Sample Airline\", \"type\":\"airline\"})" +
                " RETURNING META().id as docid, *";
        N1qlQueryResult r1 = bucket.query(N1qlQuery.simple(query));
        r1.forEach(System.out::println);
    }

    @Test
    public void givenDocument_whenInsert_thenResults() {
        Bucket bucket = bucketFactory.getTravelSampleBucket();
        JsonObject ob = JsonObject.create()
                .put("id", "1293")
                .put("name", "Sample Airline")
                .put("type", "airline");
        bucket.insert(JsonDocument.create("cust1295", ob));
    }

    @Test
    public void givenDocuments_whenBatchInsert_thenResult() {
        Bucket bucket = bucketFactory.getTravelSampleBucket();

        List<JsonDocument> documents = IntStream.rangeClosed(0,10)
          .mapToObj( i -> {
            JsonObject content = JsonObject.create()
              .put("id", i)
              .put("type", "airline")
              .put("name", "Sample Airline "  + i);
            return JsonDocument.create("cust_" + i, content);
          })
          .collect(Collectors.toList());

        List<JsonDocument> r5 = Observable
          .from(documents)
          .flatMap(doc -> bucket.async().insert(doc))
          .toList()
          .last()
          .toBlocking()
          .single();

        r5.forEach(System.out::println);
    }

    @Test
    public void givenUpdateStatement_whenQuery_thenUpdate() {
        Bucket bucket = bucketFactory.getTravelSampleBucket();
        String query2 = "UPDATE `travel-sample` USE KEYS \"cust_1\" " +
                "SET name=\"Sample Airline Updated\" RETURNING name";
        N1qlQueryResult result = bucket.query(N1qlQuery.simple(query2));
        result.forEach(System.out::println);
    }

    @Test
    public void givenDocument_whenUpsert_thenUpdate() {
        Bucket bucket = bucketFactory.getTravelSampleBucket();
        JsonObject o2 = JsonObject.create()
                .put("name", "Sample Airline Updated");
        bucket.upsert(JsonDocument.create("cust_1", o2));
    }

    @Test
    public void givenUnestUpdateStatement_whenQuery_thenResult() {
        Bucket bucket = bucketFactory.getTravelSampleBucket();
        String query3 = "UPDATE `travel-sample` USE KEYS \"cust_2\" " +
                "UNSET name RETURNING *";
        N1qlQueryResult result1 = bucket.query(N1qlQuery.simple(query3));
        result1.forEach(System.out::println);
    }

    @Test
    public void givenDeleteStatement_whenQuery_thenDelete() {
        Bucket bucket = bucketFactory.getTravelSampleBucket();
        String query4 = "DELETE FROM `travel-sample` USE KEYS \"cust_50\"";
        N1qlQueryResult result4 = bucket.query(N1qlQuery.simple(query4));
    }

    @Test
    public void givenDeleteStatement2_whenQuery_thenDelete() {
        Bucket bucket = bucketFactory.getTravelSampleBucket();
        String query5 = "DELETE FROM `travel-sample` WHERE id = 0 RETURNING *";
        N1qlQueryResult result5 = bucket.query(N1qlQuery.simple(query5));
    }


}
