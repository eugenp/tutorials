package com.baeldung.jwt.auth0;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTDecodeUnitTest {

    private static final String SECRET = "baeldung";
    private static final String ISSUER = "Baeldung";
    private static final long TOKEN_VALIDITY_IN_MILLIS = 1000L;

    private static Algorithm algorithm;
    private static JWTVerifier verifier;
    private static String jwtToken;

    @BeforeAll
    public static void setUp() {
        algorithm = Algorithm.HMAC256(SECRET);

        verifier = JWT.require(algorithm)
          .withIssuer(ISSUER)
          .build();
    }

    private static boolean isJWTExpired(DecodedJWT decodedJWT) {
        Date expiresAt = decodedJWT.getExpiresAt();
        return expiresAt.before(new Date());
    }

    private static DecodedJWT decodedJWT(String jwtToken) {
        return JWT.decode(jwtToken);
    }

    @Test
    public void givenNotExpiredJWT_whenDecoded_thenCheckingIfNotExpired() {

        jwtToken = JWT.create()
          .withIssuer(ISSUER)
          .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_VALIDITY_IN_MILLIS))
          .sign(algorithm);

        DecodedJWT decodedJWT = decodedJWT(jwtToken);
        assertNotNull(decodedJWT);
        assertFalse(isJWTExpired(decodedJWT));
    }

    @Test
    public void givenExpiredJWT_whenDecoded_thenCheckingIfExpired() {

        jwtToken = JWT.create()
          .withIssuer(ISSUER)
          .withExpiresAt(new Date(System.currentTimeMillis() - TOKEN_VALIDITY_IN_MILLIS))
          .sign(algorithm);

        DecodedJWT decodedJWT = decodedJWT(jwtToken);
        assertNotNull(decodedJWT);
        assertTrue(isJWTExpired(decodedJWT));
    }

}
