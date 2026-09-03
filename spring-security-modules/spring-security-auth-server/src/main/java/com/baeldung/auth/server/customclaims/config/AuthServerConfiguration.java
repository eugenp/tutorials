package com.baeldung.auth.server.customclaims.config;

import com.baeldung.auth.server.customclaims.components.UserInfoService;
import org.slf4j.Logger;
import org.springframework.boot.security.autoconfigure.web.servlet.SecurityFilterProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationContext;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Set;
import java.util.function.Function;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class AuthServerConfiguration {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(AuthServerConfiguration.class);


    private final UserInfoService userInfoService;

    public AuthServerConfiguration(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) {

        log.info("Creating authorization sever SecurityFilterChain");

        http.oauth2AuthorizationServer(as -> {
          http.securityMatcher(as.getEndpointsMatcher());
          as.oidc(oidc ->
            oidc.userInfoEndpoint(userInfo -> userInfo.userInfoMapper(userInfoMapper())));
        });

        http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());
        http.oauth2ResourceServer(resourceServer -> resourceServer.jwt(withDefaults()));
        http.exceptionHandling(exceptions -> exceptions.defaultAuthenticationEntryPointFor(
          new LoginUrlAuthenticationEntryPoint("/login"), createRequestMatcher()));

        return http.build();
    }

    private Function<OidcUserInfoAuthenticationContext, OidcUserInfo> userInfoMapper() {
        return context -> {
            var auth = context.getAuthentication();
            var userInfo = userInfoService.getUserInfoByUsername(auth.getName());
            return OidcUserInfo.builder()
              .subject(auth.getName())
              .email(userInfo.email())
              .name(userInfo.name())
              .givenName(userInfo.givenName())
              .familyName(userInfo.familyName())
              .locale(userInfo.locale().toLanguageTag())
              .gender(userInfo.gender())
              .birthdate(userInfo.birthdate().toString())
              .zoneinfo(userInfo.zoneId().toString())
              .preferredUsername(userInfo.username())
              .claim("account_id", userInfo.accountId().toString())
              .claim("created_at", userInfo.createdAt().toString())
              .claim("updated_at", userInfo.updatedAt().toString())
              .claim("account_expires_at", userInfo.accountExpiresAt().toString())
              .build();
        };
    }

    @Bean
    @Order(SecurityFilterProperties.BASIC_AUTH_ORDER)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) {
        http.authorizeHttpRequests(authorize -> {
              authorize.anyRequest().authenticated();
          })
          .formLogin(withDefaults());
        return http.build();
    }


    private static RequestMatcher createRequestMatcher() {
        MediaTypeRequestMatcher requestMatcher = new MediaTypeRequestMatcher(MediaType.TEXT_HTML);
        requestMatcher.setIgnoredMediaTypes(Set.of(MediaType.ALL));
        return requestMatcher;
    }

}


