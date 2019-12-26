package com.baeldung.jhipster.uaa.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Properties for UAA-based OAuth2 security.
 */
@Component
@ConfigurationProperties(prefix = "uaa", ignoreUnknownFields = false)
public class UaaProperties {
    private KeyStore keyStore = new KeyStore();

    public KeyStore getKeyStore() {
        return keyStore;
    }

    private WebClientConfiguration webClientConfiguration = new WebClientConfiguration();

    public WebClientConfiguration getWebClientConfiguration() {
        return webClientConfiguration;
    }

    /**
     * Keystore configuration for signing and verifying JWT tokens.
     */
    public static class KeyStore {
        //name of the keystore in the classpath
        private String name = "config/tls/keystore.p12";
        //password used to access the key
        private String password = "password";
        //name of the alias to fetch
        private String alias = "selfsigned";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }
    }

    public static class WebClientConfiguration {
        //validity of the short-lived access token in secs (min: 60), don't make it too long
        private int accessTokenValidityInSeconds = 5 * 60;
        //validity of the refresh token in secs (defines the duration of "remember me")
        private int refreshTokenValidityInSecondsForRememberMe = 7 * 24 * 60 * 60;
        private String clientId = "web_app";
        private String secret = "changeit";

        public int getAccessTokenValidityInSeconds() {
            return accessTokenValidityInSeconds;
        }

        public void setAccessTokenValidityInSeconds(int accessTokenValidityInSeconds) {
            this.accessTokenValidityInSeconds = accessTokenValidityInSeconds;
        }

        public int getRefreshTokenValidityInSecondsForRememberMe() {
            return refreshTokenValidityInSecondsForRememberMe;
        }

        public void setRefreshTokenValidityInSecondsForRememberMe(int refreshTokenValidityInSecondsForRememberMe) {
            this.refreshTokenValidityInSecondsForRememberMe = refreshTokenValidityInSecondsForRememberMe;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }
    }
}
