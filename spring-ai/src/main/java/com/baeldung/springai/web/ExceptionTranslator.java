package com.baeldung.springai.web;

import org.springframework.ai.openai.api.common.OpenAiApiClientErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;

@RestControllerAdvice
public class ExceptionTranslator extends ResponseEntityExceptionHandler {

    public static final String OPEN_AI_CLIENT_RAISED_EXCEPTION = "Open AI client raised exception";

    @ExceptionHandler(OpenAiApiClientErrorException.class)
    ProblemDetail handleOpenAiHttpException(OpenAiApiClientErrorException ex) {
        HttpStatus status = Optional
          .ofNullable(HttpStatus.resolve(400))
          .orElse(HttpStatus.BAD_REQUEST);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        problemDetail.setTitle(OPEN_AI_CLIENT_RAISED_EXCEPTION);
        return problemDetail;
    }
}
