package com.baeldung.inventory;

import java.io.IOException;

import org.apache.seata.core.context.RootContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SeataXidFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(SeataXidFilter.class);

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) req;
        String xid = httpRequest.getHeader(RootContext.KEY_XID);

        boolean bound = false;
        if (StringUtils.hasText(xid) && !xid.equals(RootContext.getXID())) {
            LOG.info("Receiving Seata XID: {}", xid);

            RootContext.bind(xid);
            bound = true;
        }

        try {
            chain.doFilter(req, res);
        } finally {
            // Always unbind — leaking an XID into the next request on this thread
            // is a subtle bug that causes phantom branch enrollments.
            if (bound) {
                RootContext.unbind();
            }
        }
    }
}