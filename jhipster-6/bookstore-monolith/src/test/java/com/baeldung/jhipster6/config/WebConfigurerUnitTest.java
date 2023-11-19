package com.baeldung.jhipster6.config;

import io.github.jhipster.config.JHipsterConstants;
import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.web.filter.CachingHttpHeadersFilter;
import org.h2.server.web.WebServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.*;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for the {@link WebConfigurer} class.
 */
public class WebConfigurerUnitTest {

    private WebConfigurer webConfigurer;

    private MockServletContext servletContext;

    private MockEnvironment env;

    private JHipsterProperties props;

    @BeforeEach
    public void setup() {
        servletContext = spy(new MockServletContext());
        doReturn(mock(FilterRegistration.Dynamic.class))
            .when(servletContext).addFilter(anyString(), any(Filter.class));
        doReturn(mock(ServletRegistration.Dynamic.class))
            .when(servletContext).addServlet(anyString(), any(Servlet.class));

        env = new MockEnvironment();
        props = new JHipsterProperties();

        webConfigurer = new WebConfigurer(env, props);
    }

    @Test
    public void testStartUpProdServletContext() throws ServletException {
        env.setActiveProfiles(JHipsterConstants.SPRING_PROFILE_PRODUCTION);
        webConfigurer.onStartup(servletContext);

        verify(servletContext).addFilter(eq("cachingHttpHeadersFilter"), any(CachingHttpHeadersFilter.class));
        verify(servletContext, never()).addServlet(eq("H2Console"), any(WebServlet.class));
    }

    @Test
    public void testStartUpDevServletContext() throws ServletException {
        env.setActiveProfiles(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT);
        webConfigurer.onStartup(servletContext);

        verify(servletContext, never()).addFilter(eq("cachingHttpHeadersFilter"), any(CachingHttpHeadersFilter.class));
        verify(servletContext).addServlet(eq("H2Console"), any(WebServlet.class));
    }
//
//    @Test
//    public void testCustomizeServletContainer() {
//        env.setActiveProfiles(JHipsterConstants.SPRING_PROFILE_PRODUCTION);
//        UndertowServletWebServerFactory container = new UndertowServletWebServerFactory();
//        webConfigurer.customize(container);
//        assertThat(container.getMimeMappings().get("abs")).isEqualTo("audio/x-mpeg");
//        assertThat(container.getMimeMappings().get("html")).isEqualTo("text/html;charset=utf-8");
//        assertThat(container.getMimeMappings().get("json")).isEqualTo("text/html;charset=utf-8");
//        if (container.getDocumentRoot() != null) {
//            assertThat(container.getDocumentRoot()).isEqualTo(new File("target/classes/static/"));
//        }
//    }

    @Test
    public void testCorsFilterOnApiPath() throws Exception {
        props.getCors().setAllowedOrigins(Collections.singletonList("*"));
        props.getCors().setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        props.getCors().setAllowedHeaders(Collections.singletonList("*"));
        props.getCors().setMaxAge(1800L);
        props.getCors().setAllowCredentials(true);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new WebConfigurerUnitTestController())
            .addFilters(webConfigurer.corsFilter())
            .build();

        mockMvc.perform(
            options("/api/test-cors")
                .header(HttpHeaders.ORIGIN, "other.domain.com")
                .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "POST"))
            .andExpect(status().isOk())
            .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "other.domain.com"))
            .andExpect(header().string(HttpHeaders.VARY, "Origin"))
            .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET,POST,PUT,DELETE"))
            .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true"))
            .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "1800"));

        mockMvc.perform(
            get("/api/test-cors")
                .header(HttpHeaders.ORIGIN, "other.domain.com"))
            .andExpect(status().isOk())
            .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "other.domain.com"));
    }

    @Test
    public void testCorsFilterOnOtherPath() throws Exception {
        props.getCors().setAllowedOrigins(Collections.singletonList("*"));
        props.getCors().setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        props.getCors().setAllowedHeaders(Collections.singletonList("*"));
        props.getCors().setMaxAge(1800L);
        props.getCors().setAllowCredentials(true);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new WebConfigurerUnitTestController())
            .addFilters(webConfigurer.corsFilter())
            .build();

        mockMvc.perform(
            get("/test/test-cors")
                .header(HttpHeaders.ORIGIN, "other.domain.com"))
            .andExpect(status().isOk())
            .andExpect(header().doesNotExist(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN));
    }

    @Test
    public void testCorsFilterDeactivated() throws Exception {
        props.getCors().setAllowedOrigins(null);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new WebConfigurerUnitTestController())
            .addFilters(webConfigurer.corsFilter())
            .build();

        mockMvc.perform(
            get("/api/test-cors")
                .header(HttpHeaders.ORIGIN, "other.domain.com"))
            .andExpect(status().isOk())
            .andExpect(header().doesNotExist(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN));
    }

    @Test
    public void testCorsFilterDeactivated2() throws Exception {
        props.getCors().setAllowedOrigins(new ArrayList<>());

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new WebConfigurerUnitTestController())
            .addFilters(webConfigurer.corsFilter())
            .build();

        mockMvc.perform(
            get("/api/test-cors")
                .header(HttpHeaders.ORIGIN, "other.domain.com"))
            .andExpect(status().isOk())
            .andExpect(header().doesNotExist(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN));
    }
}
