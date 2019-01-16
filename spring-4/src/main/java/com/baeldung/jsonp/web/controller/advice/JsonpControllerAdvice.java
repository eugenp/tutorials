package com.baeldung.jsonp.web.controller.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

// AbstractJsonpResponseBodyAdvice was deprecated in favor of configuring CORS properly
// We still want to cover the usage of JSON-P in our articles, therefore we don't care that it was deprecated.
@SuppressWarnings("deprecation")
@ControllerAdvice
public class JsonpControllerAdvice extends AbstractJsonpResponseBodyAdvice {

    public JsonpControllerAdvice() {
        super("callback");
    }

}
