package com.baeldung.auth.server.multitenant.config;

import com.baeldung.auth.server.multitenant.components.MultitenantJWKSource;
import com.baeldung.auth.server.multitenant.components.MultitenantOAuth2AuthorizationConsentService;
import com.baeldung.auth.server.multitenant.components.MultitenantOAuth2AuthorizationService;
import com.baeldung.auth.server.multitenant.components.MultitenantRegisteredClientRepository;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.slf4j.Logger;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContext;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

@Configuration
@EnableConfigurationProperties(MultitenantAuthServerProperties.class)
public class AuthServerConfiguration {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(AuthServerConfiguration.class);

    private final MultitenantAuthServerProperties multitenantAuthServerProperties;

    public AuthServerConfiguration(MultitenantAuthServerProperties multitenantAuthServerProperties) {
        this.multitenantAuthServerProperties = multitenantAuthServerProperties;
    }

    @Bean
    RegisteredClientRepository multitenantRegisteredClientRepository(Supplier<AuthorizationServerContext> authorizationServerContextSupplier) {

        // Create a map of repositories, indexed by tenant id
        Map<String, RegisteredClientRepository> clientRepoByTenant = new HashMap<>();
        for(var entry : multitenantAuthServerProperties.getTenants().entrySet()) {
            var mapper = new OAuth2AuthorizationServerPropertiesMapper(entry.getValue());
            log.info("Creating RegisteredClientRepository for tenant: {}", entry.getKey());
            clientRepoByTenant.put(entry.getKey(), new InMemoryRegisteredClientRepository(mapper.asRegisteredClients()));
        }

        // Return a composite repository that delegates to the appropriate tenant repository
        return new MultitenantRegisteredClientRepository(clientRepoByTenant,authorizationServerContextSupplier);
    }

    @Bean
    OAuth2AuthorizationService multitenantAuthorizationService(Supplier<AuthorizationServerContext> authorizationServerContextSupplier) {
        Map<String, OAuth2AuthorizationService> authServiceByTenant = new HashMap<>();
        for(var tenantId : multitenantAuthServerProperties.getTenants().keySet()) {
            log.info("Creating OAuth2AuthorizationService for tenant: {}", tenantId);
            authServiceByTenant.put(tenantId, new InMemoryOAuth2AuthorizationService());
        }
        return new MultitenantOAuth2AuthorizationService(authServiceByTenant,authorizationServerContextSupplier);
    }

    @Bean
    OAuth2AuthorizationConsentService multitenantAuthorizationConsentService(Supplier<AuthorizationServerContext> authorizationServerContextSupplier) {

        Map<String, OAuth2AuthorizationConsentService> authServiceByTenant = new HashMap<>();
        for(var tenantId : multitenantAuthServerProperties.getTenants().keySet()) {
            log.info("Creating OAuth2AuthorizationConsentService for tenant: {}", tenantId);
            authServiceByTenant.put(tenantId, new InMemoryOAuth2AuthorizationConsentService());
        }

        return new MultitenantOAuth2AuthorizationConsentService(authServiceByTenant,authorizationServerContextSupplier);
    }

    @Bean
    JWKSource<SecurityContext> multitenantJWKSource(Supplier<AuthorizationServerContext> authorizationServerContextSupplier) {
        Map<String, JWKSource<SecurityContext>> jwkSourceByTenant = new HashMap<>();
        for( var tenantId : multitenantAuthServerProperties.getTenants().keySet()) {
            log.info("Creating JWKSource for tenant: {}", tenantId);
            jwkSourceByTenant.put(tenantId, new ImmutableJWKSet(createJwkForTenant(tenantId)));
        }

        return new MultitenantJWKSource(jwkSourceByTenant,authorizationServerContextSupplier);
    }


    @Bean
    Supplier<AuthorizationServerContext> authorizationServerContextSupplier() {
        return () -> AuthorizationServerContextHolder.getContext();
    }


    private static  JWKSet createJwkForTenant(String tenantId) {

        // Generate the RSA key pair
        try {
            KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
            gen.initialize(2048);
            KeyPair keyPair = gen.generateKeyPair();

            // Convert to JWK format
            JWK jwk = new RSAKey.Builder((RSAPublicKey) keyPair.getPublic()).privateKey((RSAPrivateKey) keyPair.getPrivate())
              .keyUse(KeyUse.SIGNATURE)
              .keyID(UUID.randomUUID()
                .toString())
              .issueTime(new Date())
              .build();

            return new JWKSet(jwk);
        }
        catch(NoSuchAlgorithmException nex) {
            throw new RuntimeException(nex);
        }
    }


}
