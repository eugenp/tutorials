package com.baeldung.jhipster8.config;

import static com.baeldung.jhipster8.config.StaticResourcesWebConfiguration.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.CacheControl;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import tech.jhipster.config.JHipsterDefaults;
import tech.jhipster.config.JHipsterProperties;

class StaticResourcesWebConfigurerTest {

    public static final int MAX_AGE_TEST = 5;
    public StaticResourcesWebConfiguration staticResourcesWebConfiguration;
    private ResourceHandlerRegistry resourceHandlerRegistry;
    private MockServletContext servletContext;
    private WebApplicationContext applicationContext;
    private JHipsterProperties props;

    @BeforeEach
    void setUp() {
        servletContext = spy(new MockServletContext());
        applicationContext = mock(WebApplicationContext.class);
        resourceHandlerRegistry = spy(new ResourceHandlerRegistry(applicationContext, servletContext));
        props = new JHipsterProperties();
        staticResourcesWebConfiguration = spy(new StaticResourcesWebConfiguration(props));
    }

    @Test
    void shouldAppendResourceHandlerAndInitializeIt() {
        staticResourcesWebConfiguration.addResourceHandlers(resourceHandlerRegistry);

        verify(resourceHandlerRegistry, times(1)).addResourceHandler(RESOURCE_PATHS);
        verify(staticResourcesWebConfiguration, times(1)).initializeResourceHandler(any(ResourceHandlerRegistration.class));
        for (String testingPath : RESOURCE_PATHS) {
            assertThat(resourceHandlerRegistry.hasMappingForPattern(testingPath)).isTrue();
        }
    }

    @Test
    void shouldInitializeResourceHandlerWithCacheControlAndLocations() {
        CacheControl ccExpected = CacheControl.maxAge(5, TimeUnit.DAYS).cachePublic();
        when(staticResourcesWebConfiguration.getCacheControl()).thenReturn(ccExpected);
        ResourceHandlerRegistration resourceHandlerRegistration = spy(new ResourceHandlerRegistration(RESOURCE_PATHS));

        staticResourcesWebConfiguration.initializeResourceHandler(resourceHandlerRegistration);

        verify(staticResourcesWebConfiguration, times(1)).getCacheControl();
        verify(resourceHandlerRegistration, times(1)).setCacheControl(ccExpected);
        verify(resourceHandlerRegistration, times(1)).addResourceLocations(RESOURCE_LOCATIONS);
    }

    @Test
    void shouldCreateCacheControlBasedOnJhipsterDefaultProperties() {
        CacheControl cacheExpected = CacheControl.maxAge(JHipsterDefaults.Http.Cache.timeToLiveInDays, TimeUnit.DAYS).cachePublic();
        assertThat(staticResourcesWebConfiguration.getCacheControl())
            .extracting(CacheControl::getHeaderValue)
            .isEqualTo(cacheExpected.getHeaderValue());
    }

    @Test
    void shouldCreateCacheControlWithSpecificConfigurationInProperties() {
        props.getHttp().getCache().setTimeToLiveInDays(MAX_AGE_TEST);
        CacheControl cacheExpected = CacheControl.maxAge(MAX_AGE_TEST, TimeUnit.DAYS).cachePublic();
        assertThat(staticResourcesWebConfiguration.getCacheControl())
            .extracting(CacheControl::getHeaderValue)
            .isEqualTo(cacheExpected.getHeaderValue());
    }
}
