package io.jsonwebtoken.jjwtfun.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.jjwtfun.model.JwtResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Date;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class StaticJWTController extends BaseController {

    @Value("#{ @environment['jjwtfun.secret'] ?: 'secret' }")
    String secret;

    @RequestMapping(value = "/static-builder", method = GET)
    public JwtResponse fixedBuilder() throws UnsupportedEncodingException {

        String jws = Jwts.builder()
            .setIssuer("Stormpath")
            .setSubject("msilverman")
            .claim("name", "Micah Silverman")
            .claim("scope", "admins")
            .setIssuedAt(Date.from(Instant.ofEpochSecond(1466796822L)))   // Fri Jun 24 2016 15:33:42 GMT-0400 (EDT)
            .setExpiration(Date.from(Instant.ofEpochSecond(4622470422L))) // Sat Jun 24 2116 15:33:42 GMT-0400 (EDT)
            .signWith(
                SignatureAlgorithm.HS256,
                "secret".getBytes("UTF-8")
            )
            .compact();

        return new JwtResponse(jws);
    }

    @RequestMapping(value = "/parser", method = GET)
    public JwtResponse parser(@RequestParam String jwt) throws UnsupportedEncodingException {
        Jws<Claims> claims = Jwts.parser()
            .setSigningKey(secret.getBytes("UTF-8"))
            .parseClaimsJws(jwt);

        return new JwtResponse(claims);
    }

    @RequestMapping(value = "/parser-enforce", method = GET)
    public JwtResponse parserEnforce(@RequestParam String jwt) throws UnsupportedEncodingException {
        Jws<Claims> claims = Jwts.parser()
            .requireIssuer("Stormpath")
            .require("hasMotorcycle", true)
            .setSigningKey(secret.getBytes("UTF-8"))
            .parseClaimsJws(jwt);

        return new JwtResponse(claims);
    }
}
