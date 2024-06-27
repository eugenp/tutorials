package com.gateway.web.filter;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpaWebFilterTestController {

    public static final String INDEX_HTML_TEST_CONTENT = "<html>test</html>";

    @GetMapping(value = "/index.html", produces = MediaType.TEXT_HTML_VALUE)
    public String getIndexHtmlTestContent() {
        return INDEX_HTML_TEST_CONTENT;
    }
}
