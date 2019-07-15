package com.baeldung.oauth2.authorization.server.handler;

import com.baeldung.oauth2.authorization.server.PEMKeyUtils;
import com.baeldung.oauth2.authorization.server.model.AuthorizationCode;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.eclipse.microprofile.config.Config;

import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Named("authorization_code")
public class AuthorizationCodeGrantTypeHandler implements AuthorizationGrantTypeHandler {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private Config config;

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

        //JWT Header
        JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build();

        Instant now = Instant.now();
        Long expiresInMin = 30L;
        Date expiresIn = Date.from(now.plus(expiresInMin, ChronoUnit.MINUTES));

        //3. JWT Payload or claims
        JWTClaimsSet jwtClaims = new JWTClaimsSet.Builder()
                .issuer("http://localhost:9080")
                .subject(authorizationCode.getUserId())
                .claim("upn", authorizationCode.getUserId())
                .audience("http://localhost:9280")
                .claim("scope", authorizationCode.getApprovedScopes())
                .claim("groups", Arrays.asList(authorizationCode.getApprovedScopes().split(" ")))
                .expirationTime(expiresIn) // expires in 30 minutes
                .notBeforeTime(Date.from(now))
                .issueTime(Date.from(now))
                .jwtID(UUID.randomUUID().toString())
                .build();
        SignedJWT signedJWT = new SignedJWT(jwsHeader, jwtClaims);

        //4. Signing
        String signingkey = config.getValue("signingkey", String.class);
        String pemEncodedRSAPrivateKey = PEMKeyUtils.readKeyAsString(signingkey);
        RSAKey rsaKey = (RSAKey) JWK.parseFromPEMEncodedObjects(pemEncodedRSAPrivateKey);
        signedJWT.sign(new RSASSASigner(rsaKey.toRSAPrivateKey()));

        //5. Finally the JWT access token
        String accessToken = signedJWT.serialize();

        return Json.createObjectBuilder()
                .add("token_type", "Bearer")
                .add("access_token", accessToken)
                .add("expires_in", expiresInMin * 60)
                .add("scope", authorizationCode.getApprovedScopes())
                .build();
    }
}
