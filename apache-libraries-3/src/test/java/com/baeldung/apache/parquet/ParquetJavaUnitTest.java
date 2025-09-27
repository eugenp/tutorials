package com.baeldung.apache.parquet;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.avro.AvroReadSupport;
import org.apache.parquet.column.Encoding;
import org.apache.parquet.column.EncodingStats;
import org.apache.parquet.column.ParquetProperties;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.example.data.simple.SimpleGroupFactory;
import org.apache.parquet.filter2.compat.FilterCompat;
import org.apache.parquet.filter2.predicate.FilterApi;
import org.apache.parquet.filter2.predicate.FilterPredicate;
import org.apache.parquet.hadoop.ParquetFileReader;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.example.ExampleParquetWriter;
import org.apache.parquet.hadoop.example.GroupReadSupport;
import org.apache.parquet.hadoop.example.GroupWriteSupport;
import org.apache.parquet.hadoop.metadata.BlockMetaData;
import org.apache.parquet.hadoop.metadata.ColumnChunkMetaData;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.apache.parquet.hadoop.metadata.ParquetMetadata;
import org.apache.parquet.hadoop.util.HadoopInputFile;
import org.apache.parquet.hadoop.util.HadoopOutputFile;
import org.apache.parquet.io.InputFile;
import org.apache.parquet.io.OutputFile;
import org.apache.parquet.schema.LogicalTypeAnnotation;
import org.apache.parquet.schema.MessageType;
import org.apache.parquet.schema.MessageTypeParser;
import org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName;
import org.apache.parquet.schema.Types;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ParquetJavaUnitTest {

    @Nested
    class AvroUnitTest {

        private static final String PERSON_AVRO = """
            {
              "type":"record",
              "name":"Person",
              "namespace":"com.baeldung.avro",
              "fields":[
                  {"name":"name","type":"string"},
                  {"name":"age","type":"int"},
                  {"name":"city","type":["null","string"],"default":null}
              ]
            }
            """;

        private static final String NAME_ONLY = """
            {"type":"record","name":"OnlyName","fields":[{"name":"name","type":"string"}]}
            """;

        @Test
        void givenAvroSchema_whenWritingAndReadingWithAvroParquet_thenFirstRecordMatches(@TempDir java.nio.file.Path tmp) throws Exception {
            Schema schema = new Schema.Parser().parse(PERSON_AVRO);
            Configuration conf = new Configuration();
            Path hPath = new Path(tmp.resolve("people-avro.parquet").toUri());
            OutputFile out = HadoopOutputFile.fromPath(hPath, conf);

            try (ParquetWriter<GenericRecord> writer = AvroParquetWriter.<GenericRecord> builder(out)
                .withSchema(schema)
                .withConf(conf)
                .build()) {
                GenericRecord r1 = new GenericData.Record(schema);
                r1.put("name", "Carla");
                r1.put("age", 41);
                r1.put("city", "Milan");

                GenericRecord r2 = new GenericData.Record(schema);
                r2.put("name", "Diego");
                r2.put("age", 23);
                r2.put("city", null);

                writer.write(r1);
                writer.write(r2);
            }

            InputFile in = HadoopInputFile.fromPath(hPath, conf);

            try (ParquetReader<GenericRecord> reader = AvroParquetReader.<GenericRecord> builder(in)
                .withConf(conf)
                .build()) {
                GenericRecord first = reader.read();
                assertEquals("Carla", first.get("name")
                    .toString());
                assertEquals(41, first.get("age"));
            }
        }

        @Test
        void givenProjectionSchema_whenReading_thenNonProjectedFieldsAreNull(@TempDir java.nio.file.Path tmp) throws Exception {
            Configuration conf = new Configuration();

            Schema writeSchema = new Schema.Parser().parse(PERSON_AVRO);
            Path hPath = new Path(tmp.resolve("people-avro.parquet").toUri());

            try (ParquetWriter<GenericRecord> writer = AvroParquetWriter.<GenericRecord> builder(HadoopOutputFile.fromPath(hPath, conf))
                .withSchema(writeSchema)
                .withConf(conf)
                .build()) {
                    GenericRecord r = new GenericData.Record(writeSchema);
                    r.put("name", "Alice");
                    r.put("age", 30);
                    r.put("city", null);
                    writer.write(r);
            }

            Schema projection = new Schema.Parser().parse(NAME_ONLY);
            AvroReadSupport.setRequestedProjection(conf, projection);

            InputFile in = HadoopInputFile.fromPath(hPath, conf);
            try (ParquetReader<GenericRecord> reader = AvroParquetReader.<GenericRecord> builder(in)
                .withConf(conf)
                .build()) {
                    GenericRecord rec = reader.read();
                    assertNotNull(rec.get("name"));
                    assertNull(rec.get("age"));
            }
        }
    }

    @Nested
    class ExampleApiUnitTest {

        @Test
        void givenSchema_whenWritingAndReadingWithExampleApi_thenRoundtripWorks(@TempDir java.nio.file.Path tmp) throws Exception {
            String schemaString = """
                message person {
                  required binary name (UTF8);
                  required int32 age;
                  optional binary city (UTF8);
                }
                """;
            MessageType schema = MessageTypeParser.parseMessageType(schemaString);
            SimpleGroupFactory factory = new SimpleGroupFactory(schema);
            Configuration conf = new Configuration();
            Path hPath = new Path(tmp.resolve("people-example.parquet").toUri());

            try (ParquetWriter<Group> writer = ExampleParquetWriter.builder(HadoopOutputFile.fromPath(hPath, conf))
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
            try (ParquetReader<Group> reader = ParquetReader.builder(new GroupReadSupport(), hPath)
                .withConf(conf)
                .build()) {
                    Group g;
                    while ((g = reader.read()) != null) {
                        names.add(g.getBinary("name", 0)
                            .toStringUsingUTF8());
                    }
            }
            assertEquals(List.of("Alice", "Bob"), names);
        }

        @Test
        void givenAgeFilter_whenReading_thenOnlyMatchingRowsAppear(@TempDir java.nio.file.Path tmp) throws Exception {
            Configuration conf = new Configuration();

            MessageType schema = Types.buildMessage()
                .addField(Types.required(PrimitiveTypeName.BINARY)
                    .as(LogicalTypeAnnotation.stringType())
                    .named("name"))
                .addField(Types.required(PrimitiveTypeName.INT32)
                    .named("age"))
                .named("Person");

            GroupWriteSupport.setSchema(schema, conf);
            Path hPath = new Path(tmp.resolve("people-example.parquet").toUri());

            try (ParquetWriter<Group> writer = ExampleParquetWriter.builder(HadoopOutputFile.fromPath(hPath, conf))
                .withConf(conf)
                .build()) {
                SimpleGroupFactory f = new SimpleGroupFactory(schema);
                writer.write(f.newGroup()
                    .append("name", "Alice")
                    .append("age", 31));
                writer.write(f.newGroup()
                    .append("name", "Bob")
                    .append("age", 25));
            }

            FilterPredicate pred = FilterApi.gt(FilterApi.intColumn("age"), 30);
            List<String> selected = new ArrayList<>();

            try (ParquetReader<Group> reader = ParquetReader.builder(new GroupReadSupport(), hPath)
                .withConf(conf)
                .withFilter(FilterCompat.get(pred))
                .build()) {
                Group g;
                while ((g = reader.read()) != null) {
                    selected.add(g.getBinary("name", 0)
                        .toStringUsingUTF8());
                }
            }

            assertEquals(List.of("Alice"), selected);
        }
    }

    @Nested
    class WriterOptionsUnitTest {

        @Test
        void givenWriterOptions_whenBuildingWriter_thenItUsesZstdAndDictionary(@TempDir java.nio.file.Path tmp) throws Exception {
            Path hPath = new Path(tmp.resolve("opts.parquet").toUri());
            MessageType schema = MessageTypeParser.parseMessageType("message m { required binary name (UTF8); required int32 age; }");
            Configuration conf = new Configuration();
            OutputFile out = HadoopOutputFile.fromPath(hPath, conf);

            SimpleGroupFactory factory = new SimpleGroupFactory(schema);

            try (ParquetWriter<Group> writer = ExampleParquetWriter.builder(out)
                .withType(schema)
                .withConf(conf)
                .withCompressionCodec(CompressionCodecName.ZSTD)
                .withDictionaryEncoding(true)
                .withPageSize(ParquetProperties.DEFAULT_PAGE_SIZE)
                .build()) {
                    String[] names = { "alice", "bob", "carol", "dave", "erin" };
                    int[] ages = { 30, 31, 32, 33, 34 };
                    for (int i = 0; i < 5000; i++) {
                        String n = names[i % names.length];
                        int a = ages[i % ages.length];
                        writer.write(factory.newGroup()
                            .append("name", n)
                            .append("age", a));
                }
            }

            ParquetMetadata meta;
            try (ParquetFileReader reader = ParquetFileReader.open(HadoopInputFile.fromPath(hPath, conf))) {
                meta = reader.getFooter();
            }

            assertFalse(meta.getBlocks().isEmpty());

            boolean nameColumnUsedDictionary = false;

            for (BlockMetaData block : meta.getBlocks()) {
                assertFalse(block.getColumns().isEmpty());
                for (ColumnChunkMetaData col : block.getColumns()) {
                    assertEquals(CompressionCodecName.ZSTD, col.getCodec());
                    if ("name".equals(col.getPath().toDotString())) {
                        EncodingStats stats = col.getEncodingStats();
                        boolean dictByStats = stats != null && stats.hasDictionaryEncodedPages();
                        Set<Encoding> enc = col.getEncodings();
                        boolean dictByEncSet = enc.contains(Encoding.RLE_DICTIONARY) || enc.contains(Encoding.DELTA_BYTE_ARRAY);
                        boolean dictPagePresent = col.hasDictionaryPage();
                        if (dictByStats || dictByEncSet || dictPagePresent) {
                            nameColumnUsedDictionary = true;
                        }
                    }
                }
            }

            assertTrue(nameColumnUsedDictionary);
        }
    }
}
