package com.baeldung.web.service;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AsyncServiceImpl implements AsyncService {

    private static final Logger log = LoggerFactory.getLogger(AsyncService.class);

    @Async
    @Override
    public void asyncCall() {
        log.info("Inside the @Async logic: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @Override
    public Callable<Boolean> checkIfPrincipalPropagated() {
        Object before = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("Before new thread: {}", before);

        return new Callable<Boolean>() {
            public Boolean call() throws Exception {
                Object after = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                log.info("New thread: {}", after);
                return before == after;
            }
        };
    }
}
