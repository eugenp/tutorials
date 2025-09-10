package com.baeldung.apache.parquet;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.column.Encoding;
import org.apache.parquet.column.EncodingStats;
import org.apache.parquet.column.ParquetProperties;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.example.data.simple.SimpleGroupFactory;
import org.apache.parquet.hadoop.ParquetFileReader;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.example.ExampleParquetWriter;
import org.apache.parquet.hadoop.metadata.BlockMetaData;
import org.apache.parquet.hadoop.metadata.ColumnChunkMetaData;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.apache.parquet.hadoop.metadata.ParquetMetadata;
import org.apache.parquet.hadoop.util.HadoopInputFile;
import org.apache.parquet.hadoop.util.HadoopOutputFile;
import org.apache.parquet.io.OutputFile;
import org.apache.parquet.schema.MessageType;
import org.apache.parquet.schema.MessageTypeParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class WriterOptionsUnitTest {

    @Test
    void givenWriterOptions_whenBuildingWriter_thenItUsesZstdAndDictionary(@TempDir java.nio.file.Path tmp) throws Exception {

        Path hPath = new Path(tmp.resolve("opts.parquet").toUri());
        MessageType schema = MessageTypeParser.parseMessageType(
                "message m { required binary name (UTF8); required int32 age; }");
        Configuration conf = new Configuration();
        OutputFile out = HadoopOutputFile.fromPath(hPath, conf);

        SimpleGroupFactory factory = new SimpleGroupFactory(schema);

        try (ParquetWriter<Group> writer = ExampleParquetWriter
                .builder(out)
                .withType(schema)
                .withConf(conf)
                .withCompressionCodec(CompressionCodecName.ZSTD)
                .withDictionaryEncoding(true)
                .withPageSize(ParquetProperties.DEFAULT_PAGE_SIZE)
                .build()) {

            String[] names = {"alice", "bob", "carol", "dave", "erin"};
            int[] ages = {30, 31, 32, 33, 34};

            for (int i = 0; i < 5000; i++) {
                String n = names[i % names.length];
                int a = ages[i % ages.length];
                writer.write(factory.newGroup().append("name", n).append("age", a));
            }
        }

        ParquetMetadata meta;
        try (ParquetFileReader reader = ParquetFileReader.open(HadoopInputFile.fromPath(hPath, conf))) {
            meta = reader.getFooter();
        }

        assertFalse(meta.getBlocks().isEmpty(), "File should contain at least one row group");

        boolean nameColumnUsedDictionary = false;

        for (BlockMetaData block : meta.getBlocks()) {
            assertFalse(block.getColumns().isEmpty(), "Row group should contain columns");
            for (ColumnChunkMetaData col : block.getColumns()) {

                assertEquals(CompressionCodecName.ZSTD, col.getCodec(), "Column chunk should use ZSTD compression");

                if ("name".equals(col.getPath().toDotString())) {
                    EncodingStats stats = col.getEncodingStats();
                    boolean dictByStats = stats != null && stats.hasDictionaryEncodedPages();

                    Set<Encoding> enc = col.getEncodings();
                    boolean dictByEncSet = enc.contains(Encoding.RLE_DICTIONARY);

                    boolean dictPagePresent = col.hasDictionaryPage();

                    if (dictByStats || dictByEncSet || dictPagePresent) {
                        nameColumnUsedDictionary = true;
                    }
                }
            }
        }

        assertTrue(nameColumnUsedDictionary,
                "Expected 'name' column to be dictionary-encoded (with many repeated strings).");
    }
}
