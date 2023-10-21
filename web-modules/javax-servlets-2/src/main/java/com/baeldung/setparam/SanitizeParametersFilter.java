package com.baeldung.setparam;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter(urlPatterns = { "/setparam/with-sanitize.jsp" })
public class SanitizeParametersFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        chain.doFilter(new SanitizeParametersRequestWrapper(httpReq), response);
    }

}