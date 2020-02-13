package com.baeldung.spring.rest.compress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class CompressingClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(CompressingClientHttpRequestInterceptor.class);

    private static final String GZIP_ENCODING = "gzip";

    /**
     * Compress a request body using Gzip and add Http headers.
     *
     * @param req
     * @param body
     * @param exec
     * @return
     * @throws IOException
     */
    @Override
    public ClientHttpResponse intercept(HttpRequest req, byte[] body, ClientHttpRequestExecution exec)
            throws IOException {
        LOG.info("Compressing request...");
        HttpHeaders httpHeaders = req.getHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_ENCODING, GZIP_ENCODING);
        httpHeaders.add(HttpHeaders.ACCEPT_ENCODING, GZIP_ENCODING);
        return exec.execute(req, GzipUtils.compress(body));
    }

}
