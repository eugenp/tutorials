//package com.baeldung.security.oauth2.server.web;
//
//import AuthorizationGrantTypeHandler;
//import TokenResponse;
//import com.baeldung.security.oauth2.server.security.Authenticated;
//import com.nimbusds.jose.JOSEException;
//
//import javax.enterprise.inject.Instance;
//import javax.enterprise.inject.literal.NamedLiteral;
//import javax.inject.Inject;
//import javax.security.enterprise.SecurityContext;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.MultivaluedMap;
//import javax.ws.rs.core.Response;
//import java.security.Principal;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Objects;
//
///**
// * {
// * "access_token" : "acb6803a48114d9fb4761e403c17f812",
// * "token_type" : "bearer",
// * "id_token" : "eyJhbGciOiJIUzI1NiIsImprdSI6Imh0dHBzOi8vbG9jYWxob3N0OjgwODAvdWFhL3Rva2VuX2tleXMiLCJraWQiOiJsZWdhY3ktdG9rZW4ta2V5IiwidHlwIjoiSldUIn0.eyJzdWIiOiIwNzYzZTM2MS02ODUwLTQ3N2ItYjk1Ny1iMmExZjU3MjczMTQiLCJhdWQiOlsibG9naW4iXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3VhYS9vYXV0aC90b2tlbiIsImV4cCI6MTU1NzgzMDM4NSwiaWF0IjoxNTU3Nzg3MTg1LCJhenAiOiJsb2dpbiIsInNjb3BlIjpbIm9wZW5pZCJdLCJlbWFpbCI6IndyaHBONUB0ZXN0Lm9yZyIsInppZCI6InVhYSIsIm9yaWdpbiI6InVhYSIsImp0aSI6ImFjYjY4MDNhNDgxMTRkOWZiNDc2MWU0MDNjMTdmODEyIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImNsaWVudF9pZCI6ImxvZ2luIiwiY2lkIjoibG9naW4iLCJncmFudF90eXBlIjoiYXV0aG9yaXphdGlvbl9jb2RlIiwidXNlcl9uYW1lIjoid3JocE41QHRlc3Qub3JnIiwicmV2X3NpZyI6ImI3MjE5ZGYxIiwidXNlcl9pZCI6IjA3NjNlMzYxLTY4NTAtNDc3Yi1iOTU3LWIyYTFmNTcyNzMxNCIsImF1dGhfdGltZSI6MTU1Nzc4NzE4NX0.Fo8wZ_Zq9mwFks3LfXQ1PfJ4ugppjWvioZM6jSqAAQQ",
// * "refresh_token" : "f59dcb5dcbca45f981f16ce519d61486-r",
// * "expires_in" : 43199,
// * "scope" : "openid oauth.approvals",
// * "jti" : "acb6803a48114d9fb4761e403c17f812"
// * }
// */
//@Path("token")
//public class TokenEndpoint {
//
//    List<String> supportedGrantTypes = Arrays.asList("authorization_code", "password", "refresh_token", "client_credentials");
//
//    @Inject
//    private SecurityContext securityContext;
//
//    @Inject
//    Instance<AuthorizationGrantTypeHandler> authorizationGrantTypeHandlers;
//
//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    @Authenticated
//    public Response token(MultivaluedMap<String, String> params) throws JOSEException {
//        //Authenticate client with [basic] http authentication mechanism
//        Principal principal = securityContext.getCallerPrincipal();
//        Objects.requireNonNull(principal, "Client not authenticated!");
//
//        //Check grant_type params
//        String grantType = params.getFirst("grant_type");
//        Objects.requireNonNull(grantType, "grant_type params is required");
//        //authorization_code, password, refresh, client_credentials
//        if (!supportedGrantTypes.contains(grantType)) {
//            throw new RuntimeException("grant_type parameter should be one of the following :" + supportedGrantTypes);
//        }
//        AuthorizationGrantTypeHandler authorizationGrantTypeHandler = authorizationGrantTypeHandlers.select(NamedLiteral.of(grantType)).get();
//        TokenResponse tokenResponse = authorizationGrantTypeHandler.createAccessToken(principal.getName(), params);
//        Response response = Response.ok(tokenResponse)
//                .header("Cache-Control", "no-store")
//                .header("Pragma", "no-cache")
//                .build();
//        return response;
//    }
//}
