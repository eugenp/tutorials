package com.baeldung.spring.convert.databuffertomono;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DataBufferConverter { 
    public Mono<byte[]> toByteArray(Flux<DataBuffer> data) {
        return DataBufferUtils
                // Here, we'll join all DataBuffers in the Flux into a single Mono<DataBuffer>.
                .join(data)
                .flatMap(dataBuffer -> {
                    try {
                        // Next, extract the byte[] from the aggregated DataBuffer manually.
                        byte[] bytes = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(bytes);
                        return Mono.just(bytes);
                    } finally {
                        // Ensure the final aggregated DataBuffer is released.
                        DataBufferUtils.release(dataBuffer);
                    }
                });
    }
}
