package com.baeldung.metrics.filter;

import com.baeldung.metrics.service.CustomActuatorMetricService;
import com.baeldung.metrics.service.InMemoryMetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MetricFilter implements Filter {

    @Autowired
    private InMemoryMetricService metricService;

    @Autowired
    private CustomActuatorMetricService actMetricService;

    @Override
    public void init(final FilterConfig config) {
        if (metricService == null || actMetricService == null) {
            WebApplicationContext appContext = WebApplicationContextUtils
              .getRequiredWebApplicationContext(config.getServletContext());

            metricService = appContext.getBean(InMemoryMetricService.class);
            actMetricService = appContext.getBean(CustomActuatorMetricService.class);
        }
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws java.io.IOException, ServletException {
        final HttpServletRequest httpRequest = ((HttpServletRequest) request);
        final String req = httpRequest.getMethod() + " " + httpRequest.getRequestURI();

        chain.doFilter(request, response);

        final int status = ((HttpServletResponse) response).getStatus();
        metricService.increaseCount(req, status);
        actMetricService.increaseCount(status);
    }

}
