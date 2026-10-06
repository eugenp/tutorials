package com.baeldung.auth.server.customclaims.config;

import com.baeldung.auth.server.customclaims.components.UserInfoMapper;
import com.baeldung.auth.server.customclaims.components.UserInfoService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.security.autoconfigure.web.servlet.SecurityFilterProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationContext;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class AuthServerConfiguration {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(AuthServerConfiguration.class);

    private final UserInfoService userInfoService;

    @Value("${customuserinfo.enabled:true}")
    private boolean enableCustomUserInfo = true;

    public AuthServerConfiguration(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) {

        log.info("Creating authorization sever SecurityFilterChain");

        // @formatter:off
        return http.oauth2AuthorizationServer(sas -> {
          http
            .securityMatcher(sas.getEndpointsMatcher())
            .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
            .exceptionHandling(exceptions ->
              exceptions.defaultAuthenticationEntryPointFor(new LoginUrlAuthenticationEntryPoint("/login"), createRequestMatcher()));

          if ( enableCustomUserInfo ) {
            sas.oidc(oidc -> oidc
              .userInfoEndpoint(userInfo -> userInfo
                .userInfoMapper(userInfoMapper())));
          }
          else {
            sas.oidc(withDefaults());
          }
        }).build();
        // @formatter:on
    }

    private Function<OidcUserInfoAuthenticationContext, OidcUserInfo> userInfoMapper() {
        return new UserInfoMapper(userInfoService);
    }

    @Bean
    @Order(SecurityFilterProperties.BASIC_AUTH_ORDER)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) {
        // @formatter:off
        http
          .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
          .formLogin(withDefaults());
        // @formatter:on
        return http.build();
    }

    private static RequestMatcher createRequestMatcher() {
        MediaTypeRequestMatcher requestMatcher = new MediaTypeRequestMatcher(MediaType.TEXT_HTML);
        requestMatcher.setIgnoredMediaTypes(Set.of(MediaType.ALL));
        return requestMatcher;
    }

}


