package com.baeldung.formlogin.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.web.bind.annotation.*;

@RestController
@OpenAPIDefinition(info = @Info(title = "logout-endpoint"))
public class LogoutController {

    @PostMapping("logout")
    @Operation(description = "End authenticated user session")
    public void logout() {
        throw new UnsupportedOperationException();
    }
}
