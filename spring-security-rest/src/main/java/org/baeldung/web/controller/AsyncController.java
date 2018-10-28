package org.baeldung.web.controller;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.baeldung.web.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AsyncController {

    private static final Logger log = LoggerFactory.getLogger(AsyncService.class);

    @Autowired
    private AsyncService asyncService;

    @RequestMapping(method = RequestMethod.GET, value = "/async")
    @ResponseBody
    public Object standardProcessing() throws Exception {
        log.info("Outside the @Async logic - before the async call: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        asyncService.asyncCall();
        log.info("Inside the @Async logic - after the async call: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/async2")
    @ResponseBody
    public Callable<Boolean> springMVCAsyncTest() {
        return asyncService.checkIfPrincipalPropagated();
    }

}
