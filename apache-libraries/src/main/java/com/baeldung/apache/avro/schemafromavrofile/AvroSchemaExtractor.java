package com.baeldung.apache.avro.schemafromavrofile;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AvroSchemaExtractor {

    public static Schema extractSchema(String avroFilePath) throws IOException {

        File avroFile = new File(avroFilePath);
        DatumReader<GenericRecord> datumReader = new GenericDatumReader<>();

        try (DataFileReader<GenericRecord> dataFileReader = new DataFileReader<>(avroFile, datumReader)) {
            return dataFileReader.getSchema();
        }
    }

    public static List<GenericRecord> readAvroData(String avroFilePath) throws IOException {

        File avroFile = new File(avroFilePath);
        DatumReader<GenericRecord> datumReader = new GenericDatumReader<>();
        List<GenericRecord> records = new ArrayList<>();

        try (DataFileReader<GenericRecord> dataFileReader = new DataFileReader<>(avroFile, datumReader)) {
            GenericRecord record = null;
            while (dataFileReader.hasNext()) {
                record = dataFileReader.next(record);
                records.add(record);
            }
        }
        return records;
    }
}
