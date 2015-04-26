package org.baeldung.persistence.service;

import org.baeldung.persistence.dao.UserRepository;
import org.baeldung.persistence.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

@Component
public class RedditTokenService implements ClientTokenServices {

    @Autowired
    private UserRepository userReopsitory;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public RedditTokenService() {
        super();
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication) {
        logger.info("reddit ==== getAccessToken");
        final User user = (User) authentication.getPrincipal();
        final DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(user.getAccessToken());
        token.setRefreshToken(new DefaultOAuth2RefreshToken((user.getRefreshToken())));
        token.setExpiration(user.getTokenExpiration());
        return token;
    }

    @Override
    public void saveAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication, OAuth2AccessToken accessToken) {
        logger.info("reddit ==== saveAccessToken");
        final User user = (User) authentication.getPrincipal();
        user.setAccessToken(accessToken.getValue());
        if (accessToken.getRefreshToken() != null) {
            user.setRefreshToken(accessToken.getRefreshToken().getValue());
        }
        user.setTokenExpiration(accessToken.getExpiration());
        userReopsitory.save(user);
    }

    @Override
    public void removeAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication) {
        logger.info("reddit ==== removeAccessToken");
    }

}
