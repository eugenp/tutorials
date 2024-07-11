package com.baeldung.webflux.block.service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class FileService {
    @Value("${files.base.dir:/tmp/bael-7724}")
    private String filesBaseDir;

    public Mono<String> getFileContentAsString(String fileName) {
        return DataBufferUtils.read(Paths.get(filesBaseDir + "/" + fileName), DefaultDataBufferFactory.sharedInstance, DefaultDataBufferFactory.DEFAULT_INITIAL_CAPACITY)
            .map(dataBuffer -> dataBuffer.toString(StandardCharsets.UTF_8))
            .reduceWith(StringBuilder::new, StringBuilder::append)
            .map(StringBuilder::toString);
    }
}
