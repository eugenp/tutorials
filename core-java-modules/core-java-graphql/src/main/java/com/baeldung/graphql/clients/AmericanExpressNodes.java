package com.baeldung.graphql.clients;

import com.baeldung.graphql.Constants;
import com.baeldung.graphql.data.Data;
import io.aexp.nodes.graphql.GraphQLRequestEntity;
import io.aexp.nodes.graphql.GraphQLResponseEntity;
import io.aexp.nodes.graphql.GraphQLTemplate;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public final class AmericanExpressNodes {

    public static GraphQLResponseEntity<Data> callGraphQLService(String url, String query) throws IOException {
        GraphQLTemplate graphQLTemplate = new GraphQLTemplate();

        GraphQLRequestEntity requestEntity = GraphQLRequestEntity.Builder()
          .url(StringUtils.join(url, "?", Constants.QUERY_PARAMETER, "=", query))
          .request(Data.class)
          .build();

        return graphQLTemplate.query(requestEntity, Data.class);
    }

    private AmericanExpressNodes() {}

}
