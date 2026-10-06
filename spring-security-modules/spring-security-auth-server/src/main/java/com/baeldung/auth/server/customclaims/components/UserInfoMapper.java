package com.baeldung.auth.server.customclaims.components;

import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationContext;

import java.util.function.Function;

public class UserInfoMapper implements Function<OidcUserInfoAuthenticationContext, OidcUserInfo> {

    private final UserInfoService userInfoService;

    public UserInfoMapper(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Override
    public OidcUserInfo apply(OidcUserInfoAuthenticationContext context) {

        var auth = context.getAuthentication();
        var subject = auth.getName();
        var scopes = context.getAuthorization().getAuthorizedScopes();
        var userInfo = userInfoService.getUserInfoByUsername(subject);

        // Handle trivial cases first
        if ( userInfo.isEmpty() || scopes.isEmpty() || (scopes.size() == 1 && scopes.contains("openid")) ) {
            return OidcUserInfo.builder()
              .subject(subject)
              .build();
        }

        // Add claims based on the authorized scopes
        var user = userInfo.get();
        var b = OidcUserInfo.builder()
          .subject(subject);

        if ( scopes.contains("email") ) {
            b.email(user.email())
              .emailVerified(user.emailVerified());
        }

        if( scopes.contains("profile") ) {
            b.name(user.name())
              .givenName(user.givenName())
              .familyName(user.familyName())
              .locale(user.locale().toLanguageTag())
              .gender(user.gender())
              .birthdate(user.birthdate().toString())
              .zoneinfo(user.zoneId().toString())
              .preferredUsername(user.username())
              .updatedAt(user.updatedAt().toString());
        }

        // Add custom claims only if authorized. Here, we'll use the "account" as an example
        if ( scopes.contains("account") ) {
            b.claim("account_id", user.accountId().toString())
              .claim("created_at", user.createdAt().toString())
              .claim("account_expires_at", user.accountExpiresAt().toString());
        }

        // Build the final result
        return b.build();

    }
}
