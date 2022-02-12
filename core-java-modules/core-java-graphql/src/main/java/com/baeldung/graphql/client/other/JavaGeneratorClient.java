package com.baeldung.graphql.client.other;

import com.baeldung.graphql.generated.Book;
import com.baeldung.graphql.generated.util.QueryExecutor;
import com.graphql_java_generator.exception.GraphQLRequestExecutionException;
import com.graphql_java_generator.exception.GraphQLRequestPreparationException;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class JavaGeneratorClient {

    public static HttpResponse callGraphQLService(String url, String query) throws URISyntaxException, IOException, GraphQLRequestPreparationException, GraphQLRequestExecutionException {
        QueryExecutor queryExecutor = new QueryExecutor(url);
        List<Book> books = queryExecutor.allBooks("{allBooks{title}}");

        return null;
    }

    private JavaGeneratorClient() {}

}
