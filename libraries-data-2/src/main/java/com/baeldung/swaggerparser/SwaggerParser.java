package com.baeldung.swaggerparser;

import java.util.List;

import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.parser.core.models.SwaggerParseResult;

public class SwaggerParser {

    private static String OPENAPI_SPECIFICATION_STRING = "{\"openapi\":\"3.0.0\",\"info\":{\"title\":\"User APIs\",\"version\":\"1.0\"},\"servers\":[{\"url\":\"https://jsonplaceholder.typicode.com\",\"description\":\"Json Place Holder Service\"}],\"paths\":{\"/users/{id}\":{\"parameters\":[{\"schema\":{\"type\":\"integer\"},\"name\":\"id\",\"in\":\"path\",\"required\":true}],\"get\":{\"summary\":\"Fetch user by ID\",\"tags\":[\"User\"],\"responses\":{\"200\":{\"description\":\"OK\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\"},\"name\":{\"type\":\"string\"}}}}}}},\"operationId\":\"get-users-user_id\",\"description\":\"Retrieve a specific user by ID\"}}}}";

    public static void main(String[] args) {
        parseYAMLFile();
        parseJSONFile();
        parseString();
    }

    private static void parseString() {
        SwaggerParseResult result = new OpenAPIParser().readContents(OPENAPI_SPECIFICATION_STRING, null, null);

        OpenAPI openAPI = result.getOpenAPI();

        if (openAPI != null) {
            printData(openAPI);
        }
    }

    private static void parseJSONFile() {
        SwaggerParseResult result = new OpenAPIParser().readLocation("sample.yml", null, null);

        OpenAPI openAPI = result.getOpenAPI();

        if (openAPI != null) {
            printData(openAPI);
        }
    }

    private static void parseYAMLFile() {
        SwaggerParseResult result = new OpenAPIParser().readLocation("sample.json", null, null);

        OpenAPI openAPI = result.getOpenAPI();

        if (openAPI != null) {
            printData(openAPI);
        }
    }

    private static void printData(OpenAPI openAPI) {
        System.out.println(openAPI.getSpecVersion());

        Info info = openAPI.getInfo();
        System.out.println(info.getTitle());
        System.out.println(info.getVersion());

        List<Server> servers = openAPI.getServers();
        for (Server server : servers) {
            System.out.println(server.getUrl());
            System.out.println(server.getDescription());
        }

        Paths paths = openAPI.getPaths();
        paths.entrySet()
          .forEach(pathEntry -> {
              System.out.println(pathEntry.getKey());

              PathItem path = pathEntry.getValue();
              System.out.println(path.getGet()
                .getSummary());
              System.out.println(path.getGet()
                .getDescription());
              System.out.println(path.getGet()
                .getOperationId());

              ApiResponses responses = path.getGet()
                .getResponses();
              responses.entrySet()
                .forEach(responseEntry -> {
                    System.out.println(responseEntry.getKey());

                    ApiResponse response = responseEntry.getValue();
                    System.out.println(response.getDescription());
                });
          });
    }
}
