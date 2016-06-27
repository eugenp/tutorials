package io.jsonwebtoken.jjwtfun.controller;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.jjwtfun.model.JwtResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class DynamicJWTController extends BaseController {
    @Value("#{ @environment['jjwtfun.secret'] ?: 'secret' }")
    String secret;

    @RequestMapping(value = "/dynamic-builder-general", method = POST)
    public JwtResponse dynamicBuilderGeneric(@RequestBody Map<String, Object> claims) throws UnsupportedEncodingException {
        String jws =  Jwts.builder()
            .setClaims(claims)
            .signWith(
                SignatureAlgorithm.HS256,
                secret.getBytes("UTF-8")
            )
            .compact();
        return new JwtResponse(jws);
    }

    @RequestMapping(value = "/dynamic-builder-specific", method = POST)
    public JwtResponse dynamicBuilderSpecific(@RequestBody Map<String, Object> claims) throws UnsupportedEncodingException {
        JwtBuilder builder = Jwts.builder();

        claims.forEach((key, value) -> {
            switch (key) {
                case "iss":
                    ensureType(key, value,  String.class);
                    builder.setIssuer((String) value);
                    break;
                case "sub":
                    ensureType(key, value,  String.class);
                    builder.setSubject((String) value);
                    break;
                case "aud":
                    ensureType(key, value,  String.class);
                    builder.setAudience((String) value);
                    break;
                case "exp":
                    value = ensureType(key, value,  Long.class);
                    builder.setExpiration(Date.from(Instant.ofEpochSecond((Long) value)));
                    break;
                case "nbf":
                    value = ensureType(key, value,  Long.class);
                    builder.setNotBefore(Date.from(Instant.ofEpochSecond((Long) value)));
                    break;
                case "iat":
                    value = ensureType(key, value,  Long.class);
                    builder.setIssuedAt(Date.from(Instant.ofEpochSecond((Long) value)));
                    break;
                case "jti":
                    ensureType(key, value,  String.class);
                    builder.setId((String) value);
                    break;
                default:
                    builder.claim(key, value);
            }
        });

        builder.signWith(SignatureAlgorithm.HS256, secret.getBytes("UTF-8"));

        return new JwtResponse(builder.compact());
    }

    private Object ensureType(String registeredClaim, Object value, Class expectedType) {
        // we want to promote Integers to Longs in this case
        if (expectedType == Long.class && value instanceof Integer) {
            value = ((Integer) value).longValue();
        }

        boolean isCorrectType = expectedType.isInstance(value);

        if (!isCorrectType) {
            String msg = "Expected type: " + expectedType.getCanonicalName() + " for registered claim: '" +
                registeredClaim + "', but got value: " + value + " of type: " + value.getClass().getCanonicalName();
            throw new JwtException(msg);
        }

        return value;
    }
}
