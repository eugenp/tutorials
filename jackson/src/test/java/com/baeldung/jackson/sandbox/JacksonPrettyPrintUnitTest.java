package com.baeldung.jackson.sandbox;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonPrettyPrintUnitTest {

    @Test
    public final void whenDeserializing_thenCorrect() throws JsonParseException, JsonMappingException, IOException {
        // final String fileName = "src/main/resources/example1.json";
        final String fileName = "src/main/resources/example1.json";

        new File(fileName);

        printJsonFromFile(fileName);
    }

    //

    public static void printJsonFromFile(final String fileName) {
        System.out.println("-----------------");
        final ObjectMapper mapper = new ObjectMapper();
        try {
            final Object json = mapper.readValue(readFile(fileName, StandardCharsets.UTF_8), Object.class);
            System.out.println(mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(json));
        } catch (final IOException e) {
            e.printStackTrace();
        }
        System.out.println("-----------------");
    }

    static String readFile(final String path, final Charset encoding) throws IOException {
        final byte[] encoded = Files.readAllBytes(Paths.get(path));
        return encoding.decode(ByteBuffer.wrap(encoded))
            .toString();
    }

}
