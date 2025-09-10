package com.baeldung.apache.parquet;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.util.HadoopInputFile;
import org.apache.parquet.hadoop.util.HadoopOutputFile;
import org.apache.parquet.io.InputFile;
import org.apache.parquet.io.OutputFile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AvroWriteReadUnitTest {

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

    @Test
    void givenAvroSchema_whenWritingAndReadingWithAvroParquet_thenFirstRecordMatches(@TempDir java.nio.file.Path tmp) throws Exception {

        Schema schema = new Schema.Parser().parse(PERSON_AVRO);
        Path hPath = new Path(tmp.resolve("people-avro.parquet").toUri());
        Configuration conf = new Configuration();
        OutputFile out = HadoopOutputFile.fromPath(hPath, conf);

        try (ParquetWriter<GenericRecord> writer =
                 AvroParquetWriter.<GenericRecord>builder(out)
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

        try (ParquetReader<GenericRecord> reader =
                 AvroParquetReader.<GenericRecord>builder(in).withConf(conf).build()) {
            GenericRecord first = reader.read();
            assertEquals("Carla", first.get("name").toString());
            assertEquals(41, first.get("age"));
        }
    }
}

