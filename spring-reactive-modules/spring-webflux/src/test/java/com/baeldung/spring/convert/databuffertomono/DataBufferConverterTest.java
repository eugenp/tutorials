package com.baeldung.spring.convert.databuffertomono;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import reactor.core.publisher.Flux;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class DataBufferConverterTest {

    private final DataBufferConverter converter = new DataBufferConverter();
    private final DefaultDataBufferFactory factory = new DefaultDataBufferFactory();
    private final String TEST_CONTENT = "This is a long test string.";

    @Test
    void givenFluxOfDataBuffers_whenConvertedToByteArray_thenContentMatches() {
        // Setup: First, we'll manually create two DataBuffer chunks for the input Flux
        byte[] part1 = "This is a ".getBytes();
        byte[] part2 = "long test string.".getBytes();

        DataBuffer buffer1 = factory.allocateBuffer(part1.length);
        buffer1.write(part1);

        DataBuffer buffer2 = factory.allocateBuffer(part2.length);
        buffer2.write(part2);

        Flux<DataBuffer> sourceFlux = Flux.just(buffer1, buffer2);

        // Act & Assert: Here we perform conversion and block for direct assertion
        byte[] resultBytes = converter.toByteArray(sourceFlux).block();

        byte[] expectedBytes = TEST_CONTENT.getBytes();
        assertArrayEquals(expectedBytes, resultBytes, "The reconstructed byte array should match original");
    }
}
