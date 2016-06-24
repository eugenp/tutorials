package io.jsonwebtoken.jjwtfun.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class DynamicJWTController {
    @Value("#{ @environment['jjwtfun.secret'] ?: 'secret' }")
    String secret;

    Map<String, String> map = Collections.unmodifiableMap(Stream.of(
        new SimpleEntry<>("iss", ""),
        new SimpleEntry<>("a", ""),
        new SimpleEntry<>("b", ""),
        new SimpleEntry<>("c", ""),
        new SimpleEntry<>("d", ""),
        new SimpleEntry<>("e", ""),
        new SimpleEntry<>("f", ""),
        new SimpleEntry<>("g", ""),
        new SimpleEntry<>("h", ""))
        .collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue())));

    @RequestMapping(value = "/dynamic-builder", method = RequestMethod.POST)
    public String dynamicBuilder(@RequestBody Map<String, Object> claims) throws UnsupportedEncodingException {
        return Jwts.builder()
            .setClaims(claims)
            .signWith(
                SignatureAlgorithm.HS256,
                secret.getBytes("UTF-8")
            )
            .compact();
    }
}
