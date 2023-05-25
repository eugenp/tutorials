package com.baeldung.defaultglobalsecurityscheme.controller;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.defaultglobalsecurityscheme.dto.LoginDto;
import com.baeldung.defaultglobalsecurityscheme.dto.ApplicationExceptionDto;
import com.baeldung.defaultglobalsecurityscheme.dto.PingResponseDto;
import com.baeldung.defaultglobalsecurityscheme.dto.TokenDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;

@RestController
@RequestMapping("/")
public class DefaultGlobalSecuritySchemeOpenApiController {
    @RequestMapping(method = RequestMethod.POST, value = "/login", produces = { "application/json" }, consumes = { "application/json" })
    @Operation(operationId = "login", responses = {
            @ApiResponse(responseCode = "200", description = "api_key to be used in the secured-ping entry point", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = TokenDto.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized request", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationExceptionDto.class)) }) })
    @SecurityRequirements()
    public ResponseEntity<TokenDto> login(@Parameter(name = "LoginDto", description = "Login") @Valid @RequestBody(required = true) LoginDto loginDto) {
        TokenDto token = new TokenDto();
        token.setRaw("Generated Token");
        return ResponseEntity.ok(token);
    }

    @Operation(operationId = "ping", responses = {
            @ApiResponse(responseCode = "200", description = "Ping that needs an api_key attribute in the header", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PingResponseDto.class), examples = { @ExampleObject(value = "{ pong: '2022-06-17T18:30:33.465+02:00' }") }) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized request", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationExceptionDto.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden request", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationExceptionDto.class)) }) })
    @RequestMapping(method = RequestMethod.GET, value = "/ping", produces = { "application/json" })
    public ResponseEntity<PingResponseDto> ping(@RequestHeader(name = "api_key", required = false) String api_key) {
        int year = 2000;
        int month = 1;
        int dayOfMonth = 1;
        int hour = 0;
        int minute = 0;
        int second = 0;
        int nanoSeccond = 0;
        ZoneOffset offset = ZoneOffset.UTC;
        PingResponseDto response = new PingResponseDto();
        response.setPong(OffsetDateTime.of(year, month, dayOfMonth, hour, minute, second, nanoSeccond, offset));
        return ResponseEntity.ok(response);
    }
}