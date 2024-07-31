package com.baeldung.oauth2.authorization.server.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "authorization_code")
public class AuthorizationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "code")
    private String code;
    @Column(name = "client_id")
    private String clientId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "approved_scopes")
    private String approvedScopes;

    @Column(name = "redirect_uri")
    private String redirectUri;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String username) {
        this.userId = username;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getApprovedScopes() {
        return approvedScopes;
    }

    public void setApprovedScopes(String approvedScopes) {
        this.approvedScopes = approvedScopes;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }
}
