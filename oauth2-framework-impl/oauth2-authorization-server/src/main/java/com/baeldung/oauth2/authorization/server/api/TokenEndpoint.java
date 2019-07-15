package com.baeldung.oauth2.authorization.server.api;

import com.baeldung.oauth2.authorization.server.handler.AuthorizationGrantTypeHandler;
import com.baeldung.oauth2.authorization.server.model.AppDataRepository;
import com.baeldung.oauth2.authorization.server.model.Client;
import com.nimbusds.jose.JOSEException;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.literal.NamedLiteral;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Path("token")
public class TokenEndpoint {

    List<String> supportedGrantTypes = Collections.singletonList("authorization_code");

    @Inject
    private AppDataRepository appDataRepository;

    @Inject
    Instance<AuthorizationGrantTypeHandler> authorizationGrantTypeHandlers;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response token(MultivaluedMap<String, String> params,
                          @HeaderParam(HttpHeaders.AUTHORIZATION) String authHeader) throws JOSEException {

        //Check grant_type params
        String grantType = params.getFirst("grant_type");
        Objects.requireNonNull(grantType, "grant_type params is required");
        if (!supportedGrantTypes.contains(grantType)) {
            JsonObject error = Json.createObjectBuilder()
                    .add("error", "unsupported_grant_type")
                    .add("error_description", "grant type should be one of :" + supportedGrantTypes)
                    .build();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(error).build();

        }

        //Client Authentication
        String[] clientCredentials = extract(authHeader);
        String clientId = clientCredentials[0];
        String clientSecret = clientCredentials[1];
        Client client = appDataRepository.getClient(clientId);
        if (client == null || clientSecret == null || !clientSecret.equals(client.getClientSecret())) {
            JsonObject error = Json.createObjectBuilder()
                    .add("error", "invalid_client")
                    .build();
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(error).build();
        }

        AuthorizationGrantTypeHandler authorizationGrantTypeHandler = authorizationGrantTypeHandlers.select(NamedLiteral.of(grantType)).get();
        JsonObject tokenResponse = null;
        try {
            tokenResponse = authorizationGrantTypeHandler.createAccessToken(clientId, params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Response.ok(tokenResponse)
                .header("Cache-Control", "no-store")
                .header("Pragma", "no-cache")
                .build();
    }

    private String[] extract(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Basic ")) {
            return new String(Base64.getDecoder().decode(authHeader.substring(6))).split(":");
        }
        return null;
    }
}
