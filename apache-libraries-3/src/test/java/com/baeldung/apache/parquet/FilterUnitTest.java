package com.baeldung.apache.parquet;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.example.data.simple.SimpleGroupFactory;
import org.apache.parquet.filter2.compat.FilterCompat;
import org.apache.parquet.filter2.predicate.FilterApi;
import org.apache.parquet.filter2.predicate.FilterPredicate;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.example.ExampleParquetWriter;
import org.apache.parquet.hadoop.example.GroupReadSupport;
import org.apache.parquet.hadoop.example.GroupWriteSupport;
import org.apache.parquet.schema.MessageType;
import org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName;
import org.apache.parquet.schema.Types;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilterUnitTest {

    @Test
    void givenAgeFilter_whenReading_thenOnlyMatchingRowsAppear(@TempDir java.nio.file.Path tmp) throws Exception {
        Configuration conf = new Configuration();

        MessageType schema = Types.buildMessage()
                .required(PrimitiveTypeName.BINARY).named("name")
                .required(PrimitiveTypeName.INT32).named("age")
                .named("Person");

        GroupWriteSupport.setSchema(schema, conf);
        Path file = new Path(tmp.resolve("people-example.parquet").toUri());

        try (var writer = ExampleParquetWriter.builder(file)
                .withConf(conf)
                .build()) {

            SimpleGroupFactory f = new SimpleGroupFactory(schema);
            writer.write(f.newGroup().append("name", "Alice").append("age", 31));
            writer.write(f.newGroup().append("name", "Bob").append("age", 25));
        }

        FilterPredicate pred = FilterApi.gt(FilterApi.intColumn("age"), 30);
        List<String> selected = new ArrayList<>();

        try (ParquetReader<Group> reader = ParquetReader
                .builder(new GroupReadSupport(), file)
                .withConf(conf)
                .withFilter(FilterCompat.get(pred))
                .build()) {

            Group g;
            while ((g = reader.read()) != null) {
                selected.add(g.getBinary("name", 0).toStringUsingUTF8());
            }
        }

        assertEquals(List.of("Alice"), selected);
    }
}
