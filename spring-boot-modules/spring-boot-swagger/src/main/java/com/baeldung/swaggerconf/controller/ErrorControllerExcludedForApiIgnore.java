package com.baeldung.swaggerconf.controller;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Component
@RequestMapping("good-path/error-excluded-apiignore")
@RestController
@ApiIgnore
public class ErrorControllerExcludedForApiIgnore extends BasicErrorController {

    public ErrorControllerExcludedForApiIgnore(ErrorAttributes errorAttributes, ServerProperties serverProperties) {
        super(errorAttributes, serverProperties.getError());
    }

}
