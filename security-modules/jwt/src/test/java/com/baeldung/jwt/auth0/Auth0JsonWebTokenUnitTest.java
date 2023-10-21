package com.baeldung.jwt.auth0;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.IncorrectClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class Auth0JsonWebTokenUnitTest {

    private static final String SECRET = "baeldung";
    private static final String SECRET_NEW = "baeldung.com";
    private static final String ISSUER = "Baeldung";
    private static final String DATA_CLAIM = "userId";
    private static final String DATA = "1234";

    private static Algorithm algorithm;
    private static Algorithm algorithmWithDifferentSecret;
    private static JWTVerifier verifier;
    private static String jwtToken;

    @BeforeAll
    public static void setUp() {
        algorithm = Algorithm.HMAC256(SECRET);

        algorithmWithDifferentSecret = Algorithm.HMAC256(SECRET_NEW);

        verifier = JWT.require(algorithm)
          .withIssuer(ISSUER)
          .build();
    }

    private static boolean isJWTExpired(DecodedJWT decodedJWT) {
        Date expiresAt = decodedJWT.getExpiresAt();
        return expiresAt.getTime() < System.currentTimeMillis();
    }

    private static DecodedJWT verifyJWT(String jwtToken) {
        DecodedJWT decodedJWT = verifier.verify(jwtToken);
        return decodedJWT;
    }

    @Test
    public void givenJWT_whenNotExpired_thenCheckingIfNotExpired() {

        jwtToken = JWT.create()
          .withIssuer(ISSUER)
          .withClaim(DATA_CLAIM, DATA)
          .withExpiresAt(new Date(System.currentTimeMillis() + 1000L))
          .sign(algorithm);

        DecodedJWT decodedJWT = verifyJWT(jwtToken);
        assertNotNull(decodedJWT);
        assertFalse(isJWTExpired(decodedJWT));
    }

    @Test
    public void givenJWT_whenExpired_thenCheckingIfExpired() {

        jwtToken = JWT.create()
          .withIssuer(ISSUER)
          .withClaim(DATA_CLAIM, DATA)
          .withExpiresAt(new Date(System.currentTimeMillis() - 1000L))
          .sign(algorithm);

        assertThrows(TokenExpiredException.class, () -> {
            verifyJWT(jwtToken);
        });
    }

    @Test
    public void givenJWT_whenCreatedWithCustomClaim_thenCheckingForCustomClaim() {

        jwtToken = JWT.create()
          .withIssuer(ISSUER)
          .withClaim(DATA_CLAIM, DATA)
          .withExpiresAt(new Date(System.currentTimeMillis() + 1000L))
          .sign(algorithm);

        DecodedJWT decodedJWT = verifyJWT(jwtToken);
        assertNotNull(decodedJWT);

        Claim claim = decodedJWT.getClaim(DATA_CLAIM);
        assertEquals(DATA, claim.asString());
    }

    //Need to fix with JAVA-24552
    @Ignore
    public void givenJWT_whenCreatedWithNotBefore_thenThrowException() {

        jwtToken = JWT.create()
          .withIssuer(ISSUER)
          .withClaim(DATA_CLAIM, DATA)
          .withNotBefore(new Date(System.currentTimeMillis() + 10000L))
          .sign(algorithm);

        assertThrows(IncorrectClaimException.class, () -> {
            verifyJWT(jwtToken);
        });
    }

    @Test
    public void givenJWT_whenVerifyingUsingDifferentSecret_thenThrowException() {

        jwtToken = JWT.create()
          .withIssuer(ISSUER)
          .withClaim(DATA_CLAIM, DATA)
          .withExpiresAt(new Date(System.currentTimeMillis() + 1000L))
          .sign(algorithmWithDifferentSecret);

        assertThrows(SignatureVerificationException.class, () -> {
            verifyJWT(jwtToken);
        });
    }
}
