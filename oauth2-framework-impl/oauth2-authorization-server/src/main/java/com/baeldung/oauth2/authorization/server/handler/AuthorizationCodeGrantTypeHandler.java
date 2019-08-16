package com.baeldung.oauth2.authorization.server.handler;

import com.baeldung.oauth2.authorization.server.model.AuthorizationCode;

import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import java.time.LocalDateTime;

@Named("authorization_code")
public class AuthorizationCodeGrantTypeHandler extends AbstractGrantTypeHandler {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public JsonObject createAccessToken(String clientId, MultivaluedMap<String, String> params) throws Exception {
        //1. code is required
        String code = params.getFirst("code");
        if (code == null || "".equals(code)) {
            throw new WebApplicationException("invalid_grant");
        }
        AuthorizationCode authorizationCode = entityManager.find(AuthorizationCode.class, code);
        if (!authorizationCode.getExpirationDate().isAfter(LocalDateTime.now())) {
            throw new WebApplicationException("code Expired !");
        }
        String redirectUri = params.getFirst("redirect_uri");
        //redirecturi match
        if (authorizationCode.getRedirectUri() != null && !authorizationCode.getRedirectUri().equals(redirectUri)) {
            //redirectUri params should be the same as the requested redirectUri.
            throw new WebApplicationException("invalid_grant");
        }
        //client match
        if (!clientId.equals(authorizationCode.getClientId())) {
            throw new WebApplicationException("invalid_grant");
        }

        //3. JWT Payload or claims
        String accessToken = getAccessToken(clientId, authorizationCode.getUserId(), authorizationCode.getApprovedScopes());
        String refreshToken = getRefreshToken(clientId, authorizationCode.getUserId(), authorizationCode.getApprovedScopes());

        return Json.createObjectBuilder()
                .add("token_type", "Bearer")
                .add("access_token", accessToken)
                .add("expires_in", expiresInMin * 60)
                .add("scope", authorizationCode.getApprovedScopes())
                .add("refresh_token", refreshToken)
                .build();
    }
}
