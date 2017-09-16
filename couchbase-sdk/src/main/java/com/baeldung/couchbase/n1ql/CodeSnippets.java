package com.baeldung.couchbase.n1ql;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import com.couchbase.client.java.query.Statement;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.couchbase.client.java.query.Select.select;
import static com.couchbase.client.java.query.dsl.Expression.i;
import static com.couchbase.client.java.query.dsl.Expression.s;
import static com.couchbase.client.java.query.dsl.Expression.x;

public class CodeSnippets {

    public static void main(String[] args) {

        Cluster cluster = CouchbaseCluster.create("127.0.0.1");
        Bucket bucket = cluster.openBucket("test", 1, TimeUnit.MINUTES);
        Bucket bucket1 = cluster.openBucket("travel-sample", 2, TimeUnit.MINUTES);

        bucket.bucketManager().createN1qlPrimaryIndex(true, false);
        bucket1.bucketManager().createN1qlPrimaryIndex(true, false);

        insertDocument(bucket1);
        basicSelect(bucket1);
        selectStatement1(bucket1);
        selectStatement2(bucket1);
        selectStatement3DSL(bucket1);
        selectStatement4(bucket1);
        selectStatement4DSL(bucket1);
        insertStatement1(bucket1);
        insertStatement2(bucket1);
        insertStatement3Bulk(bucket1);
        updateStatement1(bucket1);
        updateStatement2Upsert(bucket1);
        updateStatement3(bucket1);
        deleteStatement1(bucket1);
        deleteStatement2(bucket1);

        bucket.close();
        bucket1.close();
        cluster.disconnect();
        System.exit(0);

    }

    private static void insertDocument(Bucket bucket) {

        JsonObject personObj = JsonObject.create()
                .put("name", "John")
                .put("email", "john@doe.com")
                .put("interests", JsonArray.from("Java", "Nigerian Jollof"));

        String id = UUID.randomUUID().toString();
        JsonDocument doc = JsonDocument.create(id, personObj);
        bucket.insert(doc);

        System.out.println(bucket.get(id));
    }

    private static void basicSelect(Bucket bucket) {

        N1qlQueryResult result
                = bucket.query(N1qlQuery.simple("SELECT * FROM test"));

        result.forEach(System.out::println);

        System.out.println("result count: " + result.info().resultCount());
        System.out.println("error count: " + result.info().errorCount());
    }

    private static void selectStatement1(Bucket bucket) {

        String query = "SELECT name FROM `travel-sample` " +
          "WHERE type = 'airport' LIMIT 100";
        N1qlQueryResult result1 = bucket.query(N1qlQuery.simple(query));

        System.out.println("Result Count " + result1.info().resultCount());

        N1qlQueryRow row = result1.allRows().get(0);
        JsonObject rowJson = row.value();
        System.out.println("Name in First Row " + rowJson.get("name"));

    }

    private static void selectStatement2(Bucket bucket) {

        JsonObject pVal = JsonObject.create().put("type", "airport");
        String query = "SELECT * FROM `travel-sample` " +
          "WHERE type = $type LIMIT 100";
        N1qlQueryResult r2 = bucket.query(N1qlQuery.parameterized(query, pVal));

        System.out.println(r2.allRows());

        List<JsonNode> list = extractJsonResult(r2);
        System.out.println(
          list.get(0).get("travel-sample").get("airportname").asText());
    }

    private static void selectStatement3DSL(Bucket bucket) {

        Statement statement = select("*")
          .from(i("travel-sample"))
          .where(x("type").eq(s("airport")))
          .limit(100);
        N1qlQueryResult r3 = bucket.query(N1qlQuery.simple(statement));

        List<JsonNode> list2 = extractJsonResult(r3);
        System.out.println("First Airport Name: " + list2.get(0).get("travel-sample").get("airportname").asText());

    }

    private static void selectStatement4(Bucket bucket) {

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

    private static void selectStatement4DSL(Bucket bucket) {

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

    private static void insertStatement1(Bucket bucket) {

        String query = "INSERT INTO `travel-sample` (KEY, VALUE) " +
                " VALUES(" +
                "\"cust1293\", " +
                "{\"id\":\"1293\",\"name\":\"Sample Airline\", \"type\":\"airline\"})" +
                " RETURNING META().id as docid, *";
        N1qlQueryResult r1 = bucket.query(N1qlQuery.simple(query));
        r1.forEach(System.out::println);
    }

    private static void insertStatement2(Bucket bucket) {

        JsonObject ob = JsonObject.create()
                .put("id", "1293")
                .put("name", "Sample Airline")
                .put("type", "airline");
        bucket.insert(JsonDocument.create("cust1295", ob));
    }

    private static void insertStatement3Bulk(Bucket bucket) {

        int docsToCreate = 10;
        List<JsonDocument> documents = new ArrayList<>();
        for (int i = 5; i < docsToCreate; i++) {
            JsonObject content = JsonObject.create()
                    .put("id", i)
                    .put("type", "airline")
                    .put("name", "Sample Airline "  + i);
            documents.add(JsonDocument.create("cust_" + i, content));
        }

        List<JsonDocument> r5 = Observable
                .from(documents)
                .flatMap(doc -> bucket.async().insert(doc))
                .toList()
                .last()
                .toBlocking()
                .single();

        r5.forEach(System.out::println);
    }

    private static void updateStatement1(Bucket bucket) {

        String query2 = "UPDATE `travel-sample` USE KEYS \"cust_1\" " +
                "SET name=\"Sample Airline Updated\" RETURNING name";
        N1qlQueryResult result = bucket.query(N1qlQuery.simple(query2));
        result.forEach(System.out::println);
    }

    private static void updateStatement2Upsert(Bucket bucket) {

        JsonObject o2 = JsonObject.create()
                .put("name", "Sample Airline Updated");
        bucket.upsert(JsonDocument.create("cust_1", o2));
    }

    private static void updateStatement3(Bucket bucket) {

        String query3 = "UPDATE `travel-sample` USE KEYS \"cust_2\" " +
                "UNSET name RETURNING *";
        N1qlQueryResult result1 = bucket.query(N1qlQuery.simple(query3));
        result1.forEach(System.out::println);
    }

    private static void deleteStatement1(Bucket bucket) {
        String query4 = "DELETE FROM `travel-sample` USE KEYS \"cust_50\"";
        N1qlQueryResult result4 = bucket.query(N1qlQuery.simple(query4));
    }

    private static void deleteStatement2(Bucket bucket) {
        String query5 = "DELETE FROM `travel-sample` WHERE id = 0 RETURNING *";
        N1qlQueryResult result5 = bucket.query(N1qlQuery.simple(query5));
    }

    private static List<JsonNode> extractJsonResult(N1qlQueryResult result) {
      ObjectMapper objectMapper = new ObjectMapper();
      return result.allRows().stream()
        .map(row -> Try.of(() -> objectMapper.readTree(row.value().toString()))
           .getOrNull())
        .collect(Collectors.toList());
    }

}
