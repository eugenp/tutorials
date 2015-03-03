package org.baeldung.config;

import java.util.Arrays;

import org.baeldung.web.schedule.ScheduledTasks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@EnableScheduling
@EnableAsync
@ComponentScan({ "org.baeldung.web" })
public class WebConfig extends WebMvcConfigurerAdapter {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public ScheduledTasks scheduledTasks(OAuth2ProtectedResourceDetails reddit) {
        ScheduledTasks s = new ScheduledTasks();
        s.setRedditRestTemplate(new OAuth2RestTemplate(reddit));
        return s;
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Configuration
    @EnableOAuth2Client
    @PropertySource("classpath:reddit.properties")
    protected static class ResourceConfiguration {

        @Value("${accessTokenUri}")
        private String accessTokenUri;

        @Value("${userAuthorizationUri}")
        private String userAuthorizationUri;

        @Value("${clientID}")
        private String clientID;

        @Value("${clientSecret}")
        private String clientSecret;

        @Bean
        public OAuth2ProtectedResourceDetails reddit() {
            AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
            details.setId("reddit");
            details.setClientId(clientID);
            details.setClientSecret(clientSecret);
            details.setAccessTokenUri(accessTokenUri);
            details.setUserAuthorizationUri(userAuthorizationUri);
            details.setTokenName("oauth_token");
            details.setScope(Arrays.asList("identity", "read", "submit"));
            details.setGrantType("authorization_code");
            return details;
        }

        @Bean
        public OAuth2RestTemplate redditRestTemplate(OAuth2ClientContext clientContext) {
            OAuth2RestTemplate template = new OAuth2RestTemplate(reddit(), clientContext);
            AccessTokenProvider accessTokenProvider = new AccessTokenProviderChain(Arrays.<AccessTokenProvider> asList(new MyAuthorizationCodeAccessTokenProvider(), new ImplicitAccessTokenProvider(), new ResourceOwnerPasswordAccessTokenProvider(),
                    new ClientCredentialsAccessTokenProvider()));
            template.setAccessTokenProvider(accessTokenProvider);
            return template;
        }
    }
}