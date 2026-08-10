package com.baeldung.shop;

import java.io.IOException;

import org.apache.seata.core.context.RootContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SeataXidClientInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(SeataXidClientInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
        throws IOException {

        String xid = RootContext.getXID();
        if (StringUtils.hasText(xid)) {
            LOG.info("Propagating Seata XID: {}", xid);

            request.getHeaders().add(RootContext.KEY_XID, xid);
        }

        return execution.execute(request, body);
    }
}