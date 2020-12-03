package com.baeldung.swaggerjwt.controller;

import java.util.Arrays;
import java.util.List;

import com.baeldung.swaggerjwt.configuration.SwaggerConfiguration;
import io.swagger.annotations.Api;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController(value = "/clients")
@Api( tags = "Clients")
public class ClientsRestController {

    @ApiOperation(value = "This method is used to get the clients.")
    @GetMapping
    public List<String> getClients() {
        return Arrays.asList("First Client", "Second Client");
    }

}
