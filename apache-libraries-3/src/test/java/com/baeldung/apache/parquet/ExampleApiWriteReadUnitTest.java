package com.baeldung.apache.parquet;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.example.data.simple.SimpleGroupFactory;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.example.ExampleParquetWriter;
import org.apache.parquet.hadoop.example.GroupReadSupport;
import org.apache.parquet.schema.MessageType;
import org.apache.parquet.schema.MessageTypeParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExampleApiWriteReadUnitTest {

    @Test
    void givenSchema_whenWritingAndReadingWithExampleApi_thenRoundtripWorks(@TempDir java.nio.file.Path tmp) throws Exception {

        String schemaString = "message person { "
                + "required binary name (UTF8); "
                + "required int32 age; "
                + "optional binary city (UTF8); "
                + "}";
        MessageType schema = MessageTypeParser.parseMessageType(schemaString);
        SimpleGroupFactory factory = new SimpleGroupFactory(schema);
        Path file = new Path(tmp.resolve("people-example.parquet").toUri());
        Configuration conf = new Configuration();

        try (ParquetWriter<Group> writer =
                 ExampleParquetWriter.builder(file)
                         .withConf(conf)
                         .withType(schema)
                         .build()) {
            writer.write(factory.newGroup()
                    .append("name", "Alice")
                    .append("age", 34)
                    .append("city", "Rome"));
            writer.write(factory.newGroup()
                    .append("name", "Bob")
                    .append("age", 29));
        }

        List<String> names = new ArrayList<>();
        try (ParquetReader<Group> reader =
                 ParquetReader.builder(new GroupReadSupport(), file)
                              .withConf(conf)
                              .build()) {
            Group g;
            while ((g = reader.read()) != null) {
                names.add(g.getBinary("name", 0).toStringUsingUTF8());
            }
        }
        assertEquals(List.of("Alice", "Bob"), names);
    }
}
