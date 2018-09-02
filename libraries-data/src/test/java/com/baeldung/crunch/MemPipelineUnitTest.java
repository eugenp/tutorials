package com.baeldung.crunch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Calendar;

import org.apache.crunch.PCollection;
import org.apache.crunch.Pipeline;
import org.apache.crunch.Source;
import org.apache.crunch.Target;
import org.apache.crunch.impl.mem.MemPipeline;
import org.apache.crunch.io.From;
import org.apache.crunch.io.To;
import org.junit.Ignore;
import org.junit.Test;

public class MemPipelineUnitTest {

    private static final String INPUT_FILE_PATH = "src/test/resources/crunch/input.txt";

    @Test
    public void givenPipeLineAndSource_whenSourceRead_thenExpectedNumberOfRecordsRead() {
        Pipeline pipeline = MemPipeline.getInstance();
        Source<String> source = From.textFile(INPUT_FILE_PATH);

        PCollection<String> lines = pipeline.read(source);

        assertEquals(21, lines.asCollection()
            .getValue()
            .size());
    }

    @Test
    public void givenPipeLine_whenTextFileRead_thenExpectedNumberOfRecordsRead() {
        Pipeline pipeline = MemPipeline.getInstance();

        PCollection<String> lines = pipeline.readTextFile(INPUT_FILE_PATH);

        assertEquals(21, lines.asCollection()
            .getValue()
            .size());
    }

    private String createOutputPath() throws IOException {
        Path path = Files.createTempDirectory("test");
        final String outputFilePath = path.toString() + File.separatorChar
            + "output.text";
        return outputFilePath;
    }

    @Test
    @Ignore("Requires Hadoop binaries")
    public void givenCollection_whenWriteCalled_fileWrittenSuccessfully()
        throws IOException {
        PCollection<String> inputStrings = MemPipeline.collectionOf("Hello",
            "Apache", "Crunch", Calendar.getInstance()
                .toString());
        final String outputFilePath = createOutputPath();
        Target target = To.textFile(outputFilePath);

        inputStrings.write(target);

        Pipeline pipeline = MemPipeline.getInstance();
        PCollection<String> lines = pipeline.readTextFile(outputFilePath);
        assertIterableEquals(inputStrings.materialize(), lines.materialize());
    }

    @Test
    @Ignore("Requires Hadoop binaries")
    public void givenPipeLine_whenWriteTextFileCalled_fileWrittenSuccessfully()
        throws IOException {
        Pipeline pipeline = MemPipeline.getInstance();
        PCollection<String> inputStrings = MemPipeline.collectionOf("Hello",
            "Apache", "Crunch", Calendar.getInstance()
                .toString());
        final String outputFilePath = createOutputPath();

        pipeline.writeTextFile(inputStrings, outputFilePath);

        PCollection<String> lines = pipeline.readTextFile(outputFilePath);
        assertIterableEquals(inputStrings.materialize(), lines.materialize());
    }

}
