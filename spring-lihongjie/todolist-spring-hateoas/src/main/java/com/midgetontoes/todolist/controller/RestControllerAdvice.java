package com.midgetontoes.todolist.controller;

import com.midgetontoes.todolist.AccessDeniedException;
import com.midgetontoes.todolist.EntityNotFoundException;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestControllerAdvice {
    @ResponseBody
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    VndErrors entityNotFoundExceptionHandler(EntityNotFoundException ex) {
        return new VndErrors("error", ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    VndErrors accessDeniedExceptionHandler(AccessDeniedException ex) {
        return new VndErrors("error", ex.getMessage());
    }
}
