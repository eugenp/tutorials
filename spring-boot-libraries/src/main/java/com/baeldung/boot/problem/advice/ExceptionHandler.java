package com.baeldung.boot.problem.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@ControllerAdvice
public class ExceptionHandler implements ProblemHandling {
    
    // The causal chain of causes is disabled by default, 
    // but we can easily enable it by overriding the behavior:
    @Override
    public boolean isCausalChainsEnabled() {
        return true;
    }

}
