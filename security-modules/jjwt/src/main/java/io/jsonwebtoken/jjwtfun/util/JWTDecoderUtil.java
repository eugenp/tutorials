package io.jsonwebtoken.jjwtfun.util;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;

import java.util.Base64;


public class JWTDecoderUtil {

    public static String decodeJWTToken(String token) {
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String[] chunks = token.split("\\.");

        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));

        return header + " " + payload;
    }

    public static boolean isTokenValid(String token, String secretKey) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        JwtParser jwtParser = Jwts.parser()
            .verifyWith(secretKeySpec)
            .build();

        try {
            jwtParser.parse(token);
        } catch (Exception e) {
            throw new Exception("Could not verify JWT token integrity!", e);
        }

        return true;
    }
}
