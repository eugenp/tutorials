package org.baeldung.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.baeldung.persistence.service.RedditTokenService;
import org.baeldung.reddit.classifier.RedditClassifier;
import org.baeldung.reddit.util.UserAgentInterceptor;
import org.baeldung.web.schedule.ScheduledTasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.http.client.ClientHttpRequestInterceptor;
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
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@EnableScheduling
@EnableAsync
@ComponentScan({ "org.baeldung.web" })
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private Environment env;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public ViewResolver viewResolver() {
        final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        super.addViewControllers(registry);
        registry.addViewController("/home.html");
    }

    @Bean
    public ScheduledTasks scheduledTasks(OAuth2ProtectedResourceDetails reddit) {
        final ScheduledTasks s = new ScheduledTasks();
        final List<ClientHttpRequestInterceptor> list = new ArrayList<ClientHttpRequestInterceptor>();
        list.add(new UserAgentInterceptor());
        final OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(reddit);
        restTemplate.setInterceptors(list);
        s.setRedditRestTemplate(restTemplate);
        return s;
    }

    @Bean
    public RedditClassifier redditClassifier() throws IOException {
        // final Resource file = new ClassPathResource("data.csv");
        final RedditClassifier redditClassifier = new RedditClassifier();
        // redditClassifier.trainClassifier(file.getFile().getAbsolutePath());
        return redditClassifier;
    }

    @Override
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
            final AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
            details.setId("reddit");
            details.setClientId(clientID);
            details.setClientSecret(clientSecret);
            details.setAccessTokenUri(accessTokenUri);
            details.setUserAuthorizationUri(userAuthorizationUri);
            details.setTokenName("oauth_token");
            details.setScope(Arrays.asList("identity", "read", "submit"));
            details.setGrantType("authorization_code");
            details.setPreEstablishedRedirectUri("http://localhost:8080/spring-security-oauth/login");
            details.setUseCurrentUri(false);
            return details;
        }

        @Bean
        public OAuth2RestTemplate redditRestTemplate(OAuth2ClientContext clientContext, RedditTokenService redditTokenService) {
            final OAuth2RestTemplate template = new OAuth2RestTemplate(reddit(), clientContext);
            final List<ClientHttpRequestInterceptor> list = new ArrayList<ClientHttpRequestInterceptor>();
            list.add(new UserAgentInterceptor());
            template.setInterceptors(list);
            final AccessTokenProviderChain accessTokenProvider = new AccessTokenProviderChain(Arrays.<AccessTokenProvider> asList(new MyAuthorizationCodeAccessTokenProvider(), new ImplicitAccessTokenProvider(), new ResourceOwnerPasswordAccessTokenProvider(),
                    new ClientCredentialsAccessTokenProvider()));
            accessTokenProvider.setClientTokenServices(redditTokenService);
            template.setAccessTokenProvider(accessTokenProvider);
            return template;
        }
    }
}
