package com.baeldung.graphql.clients;

import com.baeldung.graphql.Constants;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public final class ApacheHttpClient {

    public static HttpResponse callGraphQLService(String url, String query) throws URISyntaxException, IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        URI uri = new URIBuilder(request.getURI())
          .addParameter(Constants.QUERY_PARAMETER, query)
          .build();
        request.setURI(uri);
        return client.execute(request);
    }

    private ApacheHttpClient() {}

}
