package com.baeldung.oauth2.authorization.server.handler;

import com.baeldung.oauth2.authorization.server.PEMKeyUtils;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.eclipse.microprofile.config.Config;

import javax.inject.Inject;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

public abstract class AbstractGrantTypeHandler implements AuthorizationGrantTypeHandler {

    //Always RSA 256, but could be parametrized
    protected JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build();

    @Inject
    protected Config config;

    //30 min
    protected Long expiresInMin = 30L;

    protected JWSVerifier getJWSVerifier() throws Exception {
        String verificationkey = config.getValue("verificationkey", String.class);
        String pemEncodedRSAPublicKey = PEMKeyUtils.readKeyAsString(verificationkey);
        RSAKey rsaPublicKey = (RSAKey) JWK.parseFromPEMEncodedObjects(pemEncodedRSAPublicKey);
        return new RSASSAVerifier(rsaPublicKey);
    }

    protected JWSSigner getJwsSigner() throws Exception {
        String signingkey = config.getValue("signingkey", String.class);
        String pemEncodedRSAPrivateKey = PEMKeyUtils.readKeyAsString(signingkey);
        RSAKey rsaKey = (RSAKey) JWK.parseFromPEMEncodedObjects(pemEncodedRSAPrivateKey);
        return new RSASSASigner(rsaKey.toRSAPrivateKey());
    }

    protected String getAccessToken(String clientId, String subject, String approvedScope) throws Exception {
        //4. Signing
        JWSSigner jwsSigner = getJwsSigner();

        Instant now = Instant.now();
        //Long expiresInMin = 30L;
        Date expirationTime = Date.from(now.plus(expiresInMin, ChronoUnit.MINUTES));

        //3. JWT Payload or claims
        JWTClaimsSet jwtClaims = new JWTClaimsSet.Builder()
                .issuer("http://localhost:9080")
                .subject(subject)
                .claim("upn", subject)
                .claim("client_id", clientId)
                .audience("http://localhost:9280")
                .claim("scope", approvedScope)
                .claim("groups", Arrays.asList(approvedScope.split(" ")))
                .expirationTime(expirationTime) // expires in 30 minutes
                .notBeforeTime(Date.from(now))
                .issueTime(Date.from(now))
                .jwtID(UUID.randomUUID().toString())
                .build();
        SignedJWT signedJWT = new SignedJWT(jwsHeader, jwtClaims);
        signedJWT.sign(jwsSigner);
        return signedJWT.serialize();
    }

    protected String getRefreshToken(String clientId, String subject, String approvedScope) throws Exception {
        JWSSigner jwsSigner = getJwsSigner();
        Instant now = Instant.now();
        //6.Build refresh token
        JWTClaimsSet refreshTokenClaims = new JWTClaimsSet.Builder()
                .subject(subject)
                .claim("client_id", clientId)
                .claim("scope", approvedScope)
                //refresh token for 1 day.
                .expirationTime(Date.from(now.plus(1, ChronoUnit.DAYS)))
                .build();
        SignedJWT signedRefreshToken = new SignedJWT(jwsHeader, refreshTokenClaims);
        signedRefreshToken.sign(jwsSigner);
        return signedRefreshToken.serialize();
    }
}
