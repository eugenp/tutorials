package io.jsonwebtoken.jjwtfun.controller;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
public class DynamicJWTController {
    @Value("#{ @environment['jjwtfun.secret'] ?: 'secret' }")
    String secret;

    @RequestMapping(value = "/dynamic-builder-general", method = POST)
    public String dynamicBuilderGeneric(@RequestBody Map<String, Object> claims) throws UnsupportedEncodingException {
        return Jwts.builder()
            .setClaims(claims)
            .signWith(
                SignatureAlgorithm.HS256,
                secret.getBytes("UTF-8")
            )
            .compact();
    }

    @RequestMapping(value = "/dynamic-builder-specific", method = POST)
    public String dynamicBuilderSpecific(@RequestBody Map<String, Object> claims) throws UnsupportedEncodingException {
        JwtBuilder builder = Jwts.builder();

        claims.forEach((key, value) -> {
            switch (key) {
                case "iss":
                    builder.setIssuer((String)value);
                    break;
                case "sub":
                    builder.setSubject((String)value);
                    break;
                case "aud":
                    builder.setAudience((String)value);
                    break;
                case "exp":
                    builder.setExpiration(Date.from(Instant.ofEpochSecond(Long.parseLong((String)value))));
                    break;
                case "nbf":
                    builder.setNotBefore(Date.from(Instant.ofEpochSecond(Long.parseLong((String)value))));
                    break;
                case "iat":
                    builder.setIssuedAt(Date.from(Instant.ofEpochSecond(Long.parseLong((String)value))));
                    break;
                case "jti":
                    builder.setId((String)value);
                    break;
                default:
                    builder.claim(key, value);
            }
        });

        builder.signWith(SignatureAlgorithm.HS256, secret.getBytes("UTF-8"));

        return builder.compact();
    }
}
