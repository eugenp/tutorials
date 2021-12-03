package com.baeldung.architecture.hexagonal.server.rest.exception;

import com.baeldung.architecture.hexagonal.domain.exception.AbstractDomaineNotFoundException;
import com.baeldung.architecture.hexagonal.domain.exception.AbstractDomaineSaveException;
import com.baeldung.architecture.hexagonal.server.rest.payload.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandlerAdvice {

    @ExceptionHandler(AbstractDomaineSaveException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleInternalExpection(Exception e) {
        return buildResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleBadRequestException(Exception e) {
        return buildResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { AbstractDomaineNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleResourceNotFoundException(Exception e) {
        return buildResponse(e, HttpStatus.NOT_FOUND);
    }

    private ErrorResponse buildResponse(Exception exception, HttpStatus status) {
        if (exception instanceof MethodArgumentNotValidException) {
            String message = buildMethodArgumentNotValidExceptionMessage((MethodArgumentNotValidException) exception);
            return new ErrorResponse(message, status.getReasonPhrase());
        }
        return new ErrorResponse(exception.getMessage(), status.getReasonPhrase());
    }

    private String buildMethodArgumentNotValidExceptionMessage(MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
    }
}
