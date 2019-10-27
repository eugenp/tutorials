package com.baeldung.jest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.JestResultHandler;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.*;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.IndicesExists;
import io.searchbox.indices.aliases.AddAliasMapping;
import io.searchbox.indices.aliases.ModifyAliases;
import io.searchbox.indices.aliases.RemoveAliasMapping;

import java.io.IOException;
import java.util.*;

public class JestDemoApplication {

    public static void main(String[] args) throws IOException {

        // Demo the JestClient
        JestClient jestClient = jestClient();

        // Check an index
        JestResult result = jestClient.execute(new IndicesExists.Builder("employees").build());
        if(!result.isSucceeded()) {
            System.out.println(result.getErrorMessage());
        }

        // Create an index
        jestClient.execute(new CreateIndex.Builder("employees").build());

        // Create an index with options
        Map<String, Object> settings = new HashMap<>();
        settings.put("number_of_shards", 11);
        settings.put("number_of_replicas", 2);
        jestClient.execute(new CreateIndex.Builder("employees").settings(settings).build());

        // Create an alias, then remove it
        jestClient.execute(new ModifyAliases.Builder(
                new AddAliasMapping.Builder(
                        "employees",
                        "e")
                        .build())
                .build());
        JestResult jestResult = jestClient.execute(new ModifyAliases.Builder(
                new RemoveAliasMapping.Builder(
                        "employees",
                        "e")
                        .build())
                .build());

        if(jestResult.isSucceeded()) {
            System.out.println("Success!");
        }
        else {
            System.out.println(jestResult.getErrorMessage());
        }

        // Sample JSON for indexing

        // {
        //  "name": "Michael Pratt",
        //  "title": "Java Developer",
        //  "skills": ["java", "spring", "elasticsearch"],
        //  "yearsOfService": 2
        // }

        // Index a document from String
        ObjectMapper mapper = new ObjectMapper();
        JsonNode employeeJsonNode = mapper.createObjectNode()
                .put("name", "Michael Pratt")
                .put("title", "Java Developer")
                .put("yearsOfService", 2)
                .set("skills", mapper.createArrayNode()
                        .add("java")
                        .add("spring")
                        .add("elasticsearch"));
        jestClient.execute(new Index.Builder(employeeJsonNode.toString()).index("employees").build());

        // Index a document from Map
        Map<String, Object> employeeHashMap = new LinkedHashMap<>();
        employeeHashMap.put("name", "Michael Pratt");
        employeeHashMap.put("title", "Java Developer");
        employeeHashMap.put("yearsOfService", 2);
        employeeHashMap.put("skills", Arrays.asList("java", "spring", "elasticsearch"));
        jestClient.execute(new Index.Builder(employeeHashMap).index("employees").build());

        // Index a document from POJO
        Employee employee = new Employee();
        employee.setName("Michael Pratt");
        employee.setTitle("Java Developer");
        employee.setYearsOfService(2);
        employee.setSkills(Arrays.asList("java", "spring", "elasticsearch"));
        jestClient.execute(new Index.Builder(employee).index("employees").build());

        // Read document by ID
        Employee getResult = jestClient.execute(new Get.Builder("employees", "1").build()).getSourceAsObject(Employee.class);

        // Search documents
        String search = "{\n" +
                "  \"query\": {\n" +
                "    \"bool\": {\n" +
                "      \"must\": [\n" +
                "        { \"match\": { \"name\":   \"Michael Pratt\" }}\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "}";
        List<SearchResult.Hit<Employee, Void>> searchResults =
           jestClient.execute(new Search.Builder(search).build())
           .getHits(Employee.class);

        searchResults.forEach(hit -> {
            System.out.println(String.format("Document %s has score %s", hit.id, hit.score));
        });

        // Update document
        employee.setYearsOfService(3);
        jestClient.execute(new Update.Builder(employee).index("employees").id("1").build());

        // Delete documents
        jestClient.execute(new Delete.Builder("2") .index("employees") .build());

        // Bulk operations
        Employee employeeObject1 = new Employee();
        employee.setName("John Smith");
        employee.setTitle("Python Developer");
        employee.setYearsOfService(10);
        employee.setSkills(Arrays.asList("python"));

        Employee employeeObject2 = new Employee();
        employee.setName("Kate Smith");
        employee.setTitle("Senior JavaScript Developer");
        employee.setYearsOfService(10);
        employee.setSkills(Arrays.asList("javascript", "angular"));

        jestClient.execute(new Bulk.Builder().defaultIndex("employees")
                .addAction(new Index.Builder(employeeObject1).build())
                .addAction(new Index.Builder(employeeObject2).build())
                .addAction(new Delete.Builder("3").build()) .build());

        // Async operations
        Employee employeeObject3 = new Employee();
        employee.setName("Jane Doe");
        employee.setTitle("Manager");
        employee.setYearsOfService(20);
        employee.setSkills(Arrays.asList("managing"));

        jestClient.executeAsync( new Index.Builder(employeeObject3).build(), new JestResultHandler<JestResult>() {
            @Override public void completed(JestResult result) {
                // handle result
            }
            @Override public void failed(Exception ex) {
                // handle exception
            }
        });
    }

    private static JestClient jestClient()
    {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(
           new HttpClientConfig.Builder("http://localhost:9200")
              .multiThreaded(true)
              .defaultMaxTotalConnectionPerRoute(2)
              .maxTotalConnection(20)
              .build());
        return factory.getObject();
    }
}
