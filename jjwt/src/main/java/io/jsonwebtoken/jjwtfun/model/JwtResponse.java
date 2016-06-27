package io.jsonwebtoken.jjwtfun.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtResponse {
    private String message;
    private Status status;
    private String exceptionType;
    private String jwt;
    private Jws<Claims> claims;

    public enum Status {
        SUCCESS, ERROR
    }

    public JwtResponse() {}

    public JwtResponse(String jwt) {
        this.jwt = jwt;
        this.status = Status.SUCCESS;
    }

    public JwtResponse(Jws<Claims> claims) {
        this.claims = claims;
        this.status = Status.SUCCESS;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public Jws<Claims> getClaims() {
        return claims;
    }

    public void setClaims(Jws<Claims> claims) {
        this.claims = claims;
    }
}
