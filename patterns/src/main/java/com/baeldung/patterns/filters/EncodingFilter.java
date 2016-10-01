package com.baeldung.patterns.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(servletNames = "front-controller")
public class EncodingFilter extends BaseFilter {
    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
        this.encoding = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(
      ServletRequest request,
      ServletResponse response,
      FilterChain chain
    ) throws IOException, ServletException {
        String encoding = request.getParameter("encoding");
        if (encoding == null) {
            encoding = this.encoding;
        }
        response.setCharacterEncoding(encoding);
        chain.doFilter(request, response);
    }
}
