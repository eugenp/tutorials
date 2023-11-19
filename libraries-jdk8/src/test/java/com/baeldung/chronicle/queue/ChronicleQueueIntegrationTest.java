package com.baeldung.chronicle.queue;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.Test;

import com.baeldung.chronicle.queue.ChronicleQueue;

import net.openhft.chronicle.Chronicle;
import net.openhft.chronicle.ChronicleQueueBuilder;
import net.openhft.chronicle.ExcerptTailer;
import net.openhft.chronicle.tools.ChronicleTools;

public class ChronicleQueueIntegrationTest {

//    @Ignore
    @Test
    public void givenSetOfValues_whenWriteToQueue_thenWriteSuccesfully() throws IOException {
        File queueDir = Files.createTempDirectory("chronicle-queue").toFile();
        ChronicleTools.deleteOnExit(queueDir.getPath());

        Chronicle chronicle = ChronicleQueueBuilder.indexed(queueDir).build();
        String stringVal = "Hello World";
        int intVal = 101;
        long longVal = System.currentTimeMillis();
        double doubleVal = 90.00192091d;

        ChronicleQueue.writeToQueue(chronicle, stringVal, intVal, longVal, doubleVal);

        ExcerptTailer tailer = chronicle.createTailer();
        while (tailer.nextIndex()) {
            assertEquals(stringVal, tailer.readUTF());
            assertEquals(intVal, tailer.readInt());
            assertEquals(longVal, tailer.readLong());
            assertEquals((Double) doubleVal, (Double) tailer.readDouble());
        }
        tailer.finish();
        tailer.close();
        chronicle.close();
    }

}
