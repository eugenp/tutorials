package com.baeldung.jhipster.quotes.config;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlet.InstrumentedFilter;
import com.codahale.metrics.servlets.MetricsServlet;
import io.github.jhipster.config.JHipsterConstants;
import io.github.jhipster.config.JHipsterProperties;
import io.undertow.Undertow;
import io.undertow.Undertow.Builder;
import io.undertow.UndertowOptions;

import org.h2.server.web.WebServlet;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.xnio.OptionMap;

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
 * Unit tests for the WebConfigurer class.
 *
 * @see WebConfigurer
 */
public class WebConfigurerTest {

    private WebConfigurer webConfigurer;

    private MockServletContext servletContext;

    private MockEnvironment env;

    private JHipsterProperties props;

    private MetricRegistry metricRegistry;

    @Before
    public void setup() {
        servletContext = spy(new MockServletContext());
        doReturn(mock(FilterRegistration.Dynamic.class))
            .when(servletContext).addFilter(anyString(), any(Filter.class));
        doReturn(mock(ServletRegistration.Dynamic.class))
            .when(servletContext).addServlet(anyString(), any(Servlet.class));

        env = new MockEnvironment();
        props = new JHipsterProperties();

        webConfigurer = new WebConfigurer(env, props);
        metricRegistry = new MetricRegistry();
        webConfigurer.setMetricRegistry(metricRegistry);
    }

    @Test
    public void testStartUpProdServletContext() throws ServletException {
        env.setActiveProfiles(JHipsterConstants.SPRING_PROFILE_PRODUCTION);
        webConfigurer.onStartup(servletContext);

        assertThat(servletContext.getAttribute(InstrumentedFilter.REGISTRY_ATTRIBUTE)).isEqualTo(metricRegistry);
        assertThat(servletContext.getAttribute(MetricsServlet.METRICS_REGISTRY)).isEqualTo(metricRegistry);
        verify(servletContext).addFilter(eq("webappMetricsFilter"), any(InstrumentedFilter.class));
        verify(servletContext).addServlet(eq("metricsServlet"), any(MetricsServlet.class));
        verify(servletContext, never()).addServlet(eq("H2Console"), any(WebServlet.class));
    }

    @Test
    public void testStartUpDevServletContext() throws ServletException {
        env.setActiveProfiles(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT);
        webConfigurer.onStartup(servletContext);

        assertThat(servletContext.getAttribute(InstrumentedFilter.REGISTRY_ATTRIBUTE)).isEqualTo(metricRegistry);
        assertThat(servletContext.getAttribute(MetricsServlet.METRICS_REGISTRY)).isEqualTo(metricRegistry);
        verify(servletContext).addFilter(eq("webappMetricsFilter"), any(InstrumentedFilter.class));
        verify(servletContext).addServlet(eq("metricsServlet"), any(MetricsServlet.class));
        verify(servletContext).addServlet(eq("H2Console"), any(WebServlet.class));
    }

    @Test
    public void testCustomizeServletContainer() {
        env.setActiveProfiles(JHipsterConstants.SPRING_PROFILE_PRODUCTION);
        UndertowServletWebServerFactory container = new UndertowServletWebServerFactory();
        webConfigurer.customize(container);
        assertThat(container.getMimeMappings().get("abs")).isEqualTo("audio/x-mpeg");
        assertThat(container.getMimeMappings().get("html")).isEqualTo("text/html;charset=utf-8");
        assertThat(container.getMimeMappings().get("json")).isEqualTo("text/html;charset=utf-8");

        Builder builder = Undertow.builder();
        container.getBuilderCustomizers().forEach(c -> c.customize(builder));
        OptionMap.Builder serverOptions = (OptionMap.Builder) ReflectionTestUtils.getField(builder, "serverOptions");
        assertThat(serverOptions.getMap().get(UndertowOptions.ENABLE_HTTP2)).isNull();
    }

    @Test
    public void testUndertowHttp2Enabled() {
        props.getHttp().setVersion(JHipsterProperties.Http.Version.V_2_0);
        UndertowServletWebServerFactory container = new UndertowServletWebServerFactory();
        webConfigurer.customize(container);
        Builder builder = Undertow.builder();
        container.getBuilderCustomizers().forEach(c -> c.customize(builder));
        OptionMap.Builder serverOptions = (OptionMap.Builder) ReflectionTestUtils.getField(builder, "serverOptions");
        assertThat(serverOptions.getMap().get(UndertowOptions.ENABLE_HTTP2)).isTrue();
    }

    @Test
    public void testCorsFilterOnApiPath() throws Exception {
        props.getCors().setAllowedOrigins(Collections.singletonList("*"));
        props.getCors().setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        props.getCors().setAllowedHeaders(Collections.singletonList("*"));
        props.getCors().setMaxAge(1800L);
        props.getCors().setAllowCredentials(true);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new WebConfigurerTestController())
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

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new WebConfigurerTestController())
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

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new WebConfigurerTestController())
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

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new WebConfigurerTestController())
            .addFilters(webConfigurer.corsFilter())
            .build();

        mockMvc.perform(
            get("/api/test-cors")
                .header(HttpHeaders.ORIGIN, "other.domain.com"))
            .andExpect(status().isOk())
            .andExpect(header().doesNotExist(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN));
    }
}
