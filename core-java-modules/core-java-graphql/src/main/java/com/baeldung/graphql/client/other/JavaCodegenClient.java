package com.baeldung.graphql.client.other;

import com.baeldung.graphql.generated.AllBooksQueryRequest;
import com.baeldung.graphql.generated.AllBooksQueryResponse;
import com.baeldung.graphql.generated.BookResponseProjection;
import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLRequest;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class JavaCodegenClient {

    public static HttpResponse callGraphQLService(String url, String query) throws URISyntaxException, IOException {
        AllBooksQueryRequest allBooksQueryRequest = new AllBooksQueryRequest();
        BookResponseProjection bookResponseProjection = new BookResponseProjection().all$();
        GraphQLRequest request = new GraphQLRequest(allBooksQueryRequest, bookResponseProjection);

        AllBooksQueryResponse response; //???
        return null;
    }

    private JavaCodegenClient() {}

}
