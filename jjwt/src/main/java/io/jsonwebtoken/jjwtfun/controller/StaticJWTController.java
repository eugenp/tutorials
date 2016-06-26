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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class StaticJWTController {

    @RequestMapping(value = "/static-builder", method = GET)
    public String fixedBuilder() throws UnsupportedEncodingException {

        String jws = Jwts.builder()
            .setIssuer("Stormpath")
            .setSubject("msilverman")
            .claim("name", "Micah Silverman")
            .claim("scope", "admins")
            .setIssuedAt(Date.from(Instant.ofEpochSecond(1466796822)))   // Fri Jun 24 2016 15:33:42 GMT-0400 (EDT)
            .setExpiration(Date.from(Instant.ofEpochSecond(1466883222))) // Sat Jun 25 2016 15:33:42 GMT-0400 (EDT)
            .signWith(
                SignatureAlgorithm.HS256,
                "secret".getBytes("UTF-8")
            )
            .compact();

        return jws;
    }

    @RequestMapping(value = "/parser", method = GET)
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
