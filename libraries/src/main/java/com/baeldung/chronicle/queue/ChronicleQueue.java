package com.baeldung.chronicle.queue;

import java.io.IOException;

import net.openhft.chronicle.Chronicle;
import net.openhft.chronicle.ExcerptAppender;

public class ChronicleQueue {

    static void writeToQueue(Chronicle chronicle, String stringValue, int intValue, long longValue, double doubleValue) throws IOException {
        ExcerptAppender appender = chronicle.createAppender();
        appender.startExcerpt();
        appender.writeUTF(stringValue);
        appender.writeInt(intValue);
        appender.writeLong(longValue);
        appender.writeDouble(doubleValue);
        appender.finish();
        appender.close();
    }

}
