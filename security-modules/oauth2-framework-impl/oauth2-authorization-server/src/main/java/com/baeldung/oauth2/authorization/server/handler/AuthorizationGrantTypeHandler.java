package com.baeldung.oauth2.authorization.server.handler;

import javax.json.JsonObject;
import javax.ws.rs.core.MultivaluedMap;

public interface AuthorizationGrantTypeHandler {
    JsonObject createAccessToken(String clientId, MultivaluedMap<String, String> params) throws Exception;
}
