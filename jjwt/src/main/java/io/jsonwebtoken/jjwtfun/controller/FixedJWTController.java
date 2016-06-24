package io.jsonwebtoken.jjwtfun.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class FixedJWTController {

    @RequestMapping("/fixed-builder")
    public String fixedBuilder() throws UnsupportedEncodingException {

        String jws = Jwts.builder()
            .setSubject("msilverman")
            .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
            .claim("name", "Micah Silverman")
            .claim("scope", "admins")
            .signWith(
                SignatureAlgorithm.HS256,
                "secret".getBytes("UTF-8")
            )
            .compact();

        return jws;
    }

    @RequestMapping("/fixed-parser")
    public Jws<Claims> fixedParser(@RequestParam String jws) throws UnsupportedEncodingException {
        Jws<Claims> claims = Jwts.parser()
            .setSigningKey("secret".getBytes("UTF-8"))
            .parseClaimsJws(jws);

        return claims;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({SignatureException.class, MalformedJwtException.class})
    public Map<String, String> exception(Exception e) {
        Map<String, String> response = new HashMap<>();
        response.put("status", "ERROR");
        response.put("message", e.getMessage());
        response.put("exception-type", e.getClass().getName());
        return response;
    }
}
