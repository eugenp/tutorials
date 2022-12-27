package com.baeldung.jwt.auth0;

import java.util.Date;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class Auth0JsonWebToken {

    private static final String SECRET = "baeldung";
    private static final String ISSUER = "Baeldung";
    private static final String SUBJECT = "Baeldung Details";
    private static final String DATA_CLAIM = "userId";
    private static final String DATA = "1234";
    private static final long TOKEN_VALIDITY_IN_MILLIS = 5000L;

    private static Algorithm algorithm;
    private static JWTVerifier verifier;

    public static void initialize() {
        algorithm = Algorithm.HMAC256(SECRET);

        verifier = JWT.require(algorithm)
          .withIssuer(ISSUER)
          .build();
    }

    private static String createJWT() {
        String jwtToken = JWT.create()
          .withIssuer(ISSUER)
          .withSubject(SUBJECT)
          .withClaim(DATA_CLAIM, DATA)
          .withIssuedAt(new Date())
          .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_VALIDITY_IN_MILLIS))
          .withJWTId(UUID.randomUUID()
            .toString())
          .withNotBefore(new Date(System.currentTimeMillis() + 1000L))
          .sign(algorithm);

        return jwtToken;
    }

    private static DecodedJWT verifyJWT(String jwtToken) {
        try {
            DecodedJWT decodedJWT = verifier.verify(jwtToken);
            return decodedJWT;
        } catch (JWTVerificationException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static boolean isJWTExpired(DecodedJWT decodedJWT) {
        Date expiresAt = decodedJWT.getExpiresAt();
        return expiresAt.getTime() < System.currentTimeMillis();
    }

    private static String getClaim(DecodedJWT decodedJWT, String claimName) {
        Claim claim = decodedJWT.getClaim(claimName);
        return claim != null ? claim.asString() : null;
    }

    public static void main(String args[]) throws InterruptedException {

        initialize();

        String jwtToken = createJWT();
        System.out.println("Created JWT : " + jwtToken);

        DecodedJWT decodedJWT = verifyJWT(jwtToken);
        if (decodedJWT == null) {
            System.out.println("JWT Verification Failed");
        }

        Thread.sleep(1000L);

        decodedJWT = verifyJWT(jwtToken);
        if (decodedJWT != null) {
            System.out.println("Token Issued At : " + decodedJWT.getIssuedAt());
            System.out.println("Token Expires At : " + decodedJWT.getExpiresAt());
            System.out.println("Subject : " + decodedJWT.getSubject());
            System.out.println("Data : " + getClaim(decodedJWT, DATA_CLAIM));
            System.out.println("Header : " + decodedJWT.getHeader());
            System.out.println("Payload : " + decodedJWT.getPayload());
            System.out.println("Signature : " + decodedJWT.getSignature());
            System.out.println("Algorithm : " + decodedJWT.getAlgorithm());
            System.out.println("JWT Id : " + decodedJWT.getId());

            Boolean isExpired = isJWTExpired(decodedJWT);
            System.out.println("Is Expired : " + isExpired);
        }
    }

}
