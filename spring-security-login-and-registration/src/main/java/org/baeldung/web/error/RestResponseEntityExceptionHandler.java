package org.baeldung.web.error;

import org.springframework.mail.MailAuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    public RestResponseEntityExceptionHandler() {
        super();
    }

    // API

    // 500
    @ExceptionHandler({ MailAuthenticationException.class })
    public @ResponseBody String handleMail(final RuntimeException ex, final WebRequest request) throws JsonProcessingException {
        logger.error("500 Status Code", ex);
        return new ObjectMapper().writeValueAsString("MailError");
    }

    @ExceptionHandler({ NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class })
    public @ResponseBody String handleInternal(final RuntimeException ex, final WebRequest request) throws JsonProcessingException {
        logger.error("500 Status Code", ex);
        return new ObjectMapper().writeValueAsString("InternalError");
    }

}
