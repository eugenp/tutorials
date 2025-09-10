package com.baeldung.apache.parquet;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.avro.AvroReadSupport;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.util.HadoopInputFile;
import org.apache.parquet.hadoop.util.HadoopOutputFile;
import org.apache.parquet.io.InputFile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.*;

public class AvroProjectionUnitTest {

    private static final String NAME_ONLY = """
        {"type":"record","name":"OnlyName","fields":[{"name":"name","type":"string"}]}
        """;

    private static final String PERSON = """
        {"type":"record","name":"Person","fields":[
          {"name":"name","type":"string"},
          {"name":"age","type":"int"}]}
        """;

    @Test
    void givenProjectionSchema_whenReading_thenNonProjectedFieldsAreNull(@TempDir java.nio.file.Path tmp) throws Exception {
        Configuration conf = new Configuration();

        Schema writeSchema = new Schema.Parser().parse(PERSON);
        Path hPath = new Path(tmp.resolve("people-avro.parquet").toUri());

        try (ParquetWriter<GenericRecord> writer =
                 AvroParquetWriter.<GenericRecord>builder(HadoopOutputFile.fromPath(hPath, conf))
                     .withSchema(writeSchema)
                     .withConf(conf)
                     .build()) {

            GenericRecord r = new GenericData.Record(writeSchema);
            r.put("name", "Alice");
            r.put("age", 30);
            writer.write(r);
        }

        Schema projection = new Schema.Parser().parse(NAME_ONLY);
        AvroReadSupport.setRequestedProjection(conf, projection);

        InputFile in = HadoopInputFile.fromPath(hPath, conf);
        try (ParquetReader<GenericRecord> reader =
                 AvroParquetReader.<GenericRecord>builder(in).withConf(conf).build()) {

            GenericRecord rec = reader.read();
            assertNotNull(rec.get("name"));
            assertNull(rec.get("age"));
        }
    }
}
