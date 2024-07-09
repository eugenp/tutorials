package com.baeldung.athena.initialization;

import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import com.baeldung.athena.service.QueryService;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AthenaInitializer implements ApplicationRunner {

    private final QueryService queryService;
    private final ResourcePatternResolver resourcePatternResolver;

    private static final String ATHENA_INIT_SCRIPT_PATTERN = "classpath:athena-init/*.sql";

    @Override
    @SneakyThrows
    public void run(ApplicationArguments args) {
        final var initScripts = resourcePatternResolver.getResources(ATHENA_INIT_SCRIPT_PATTERN);
        for (final var script : initScripts) {
            final var sqlScript = FileUtils.readFileToString(script.getFile(), StandardCharsets.UTF_8);
            queryService.execute(sqlScript, Void.class);
            log.info("Successfully executed {}.", script.getFilename());
        }
    }

}