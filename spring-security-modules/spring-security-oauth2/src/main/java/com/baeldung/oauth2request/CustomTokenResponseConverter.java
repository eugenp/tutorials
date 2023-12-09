package com.baeldung.oauth2request;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.StringUtils;

public class CustomTokenResponseConverter implements Converter<Map<String, Object>, OAuth2AccessTokenResponse> {
    private static final Set<String> TOKEN_RESPONSE_PARAMETER_NAMES = Stream.of(
        OAuth2ParameterNames.ACCESS_TOKEN, 
        OAuth2ParameterNames.TOKEN_TYPE, 
        OAuth2ParameterNames.EXPIRES_IN, 
        OAuth2ParameterNames.REFRESH_TOKEN, 
        OAuth2ParameterNames.SCOPE) .collect(Collectors.toSet());

    @Override
    public OAuth2AccessTokenResponse convert(Map<String, Object> tokenResponseParameters) {
        Object accessToken = tokenResponseParameters.get(OAuth2ParameterNames.ACCESS_TOKEN);

        OAuth2AccessToken.TokenType accessTokenType = null;
        if (OAuth2AccessToken.TokenType.BEARER.getValue()
            .equalsIgnoreCase(tokenResponseParameters.get(OAuth2ParameterNames.TOKEN_TYPE).toString())) {
            accessTokenType = OAuth2AccessToken.TokenType.BEARER;
        }

        long expiresIn = 0;
        if (tokenResponseParameters.containsKey(OAuth2ParameterNames.EXPIRES_IN)) {
            try {
                expiresIn = Long.parseLong(tokenResponseParameters.get(OAuth2ParameterNames.EXPIRES_IN).toString());
            } catch (NumberFormatException ex) {
            }
        }

        Set<String> scopes = Collections.emptySet();
        if (tokenResponseParameters.containsKey(OAuth2ParameterNames.SCOPE)) {
            Object scope = tokenResponseParameters.get(OAuth2ParameterNames.SCOPE);
            scopes = Arrays.stream(StringUtils.delimitedListToStringArray(scope.toString(), " "))
                .collect(Collectors.toSet());
        }

        Object refreshToken = tokenResponseParameters.get(OAuth2ParameterNames.REFRESH_TOKEN);

        Map<String, Object> additionalParameters = new LinkedHashMap<>();
        tokenResponseParameters.entrySet()
            .stream()
            .filter(e -> !TOKEN_RESPONSE_PARAMETER_NAMES.contains(e.getKey()))
            .forEach(e -> additionalParameters.put(e.getKey(), e.getValue()));

        return OAuth2AccessTokenResponse.withToken(accessToken.toString())
            .tokenType(accessTokenType)
            .expiresIn(expiresIn)
            .scopes(scopes)
            .refreshToken(refreshToken.toString())
            .additionalParameters(additionalParameters)
            .build();
    }

}
