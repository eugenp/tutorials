package com.baeldung.contentsecuritypolicy;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
public class ContentSecurityPolicyController {
    private static final Logger logger = LoggerFactory.getLogger(ContentSecurityPolicyController.class);

    @PostMapping("/report")
    public void report(HttpServletRequest request) throws IOException {
        if (logger.isInfoEnabled()) {
            logger.info("Report: {}", IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8));
        }
    }
}
