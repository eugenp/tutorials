package com.baeldung.parameterized.logging;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FluentLoggingPlayground {

    public static void main(String[] args) {

        Exception exceptionCause = new Exception(new IllegalArgumentException("Something unprocessable"));

        log.atInfo()
            .setMessage("App is running at {}, zone = {}")
            .addArgument(LocalDateTime.now())
            .addArgument(ZonedDateTime.now().getZone())
            .log();

        log.atInfo()
            .setMessage("App is running at {}, zone = {}")
            .addArgument(LocalDateTime.now())
            .addArgument(ZonedDateTime.now().getZone())
            .setCause(exceptionCause)
            .log();

        log.atInfo()
            .setMessage("App is running at")
            .addKeyValue("time", LocalDateTime.now())
            .addKeyValue("zone", ZonedDateTime.now().getZone())
            .setCause(exceptionCause)
            .log();

    }
}
