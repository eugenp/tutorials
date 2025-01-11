package com.baeldung.apache.avrotojson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.io.JsonEncoder;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;

public class AvroFileToJsonFile {
    // Method to infer schema for Point class
    public Schema inferSchema(Point p) {
        return ReflectData.get().getSchema(p.getClass());
    }

    // Method to convert object to JSON using JsonEncoder
    public String convertObjectToJson(Point p, Schema schema) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            GenericDatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(schema);
            // Use GenericRecord since we're using a manually built schema
            GenericRecord genericRecord = new GenericData.Record(schema);
            genericRecord.put("x", p.getX());
            genericRecord.put("y", p.getY());
            // Use JSON encoder to serialize GenericRecord to JSON
            Encoder encoder = EncoderFactory.get().jsonEncoder(schema, outputStream);
            datumWriter.write(genericRecord, encoder);
            encoder.flush();
            outputStream.close();
            return outputStream.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Method to write Avro file using GenericRecord
    public void writeAvroToFile(Schema schema, List<Point> records, File writeLocation) {
        try {
            // Delete file if it exists
            if (writeLocation.exists()) {
                if (!writeLocation.delete()) {
                    System.err.println("Failed to delete existing file.");
                    return;
                }
            }
            // Create the Avro file writer
            GenericDatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(schema);
            DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(datumWriter);
            dataFileWriter.create(schema, writeLocation);
            // Write each record as a GenericRecord
            for (Point record : records) {
                GenericRecord genericRecord = new GenericData.Record(schema);
                genericRecord.put("x", record.getX());
                genericRecord.put("y", record.getY());
                dataFileWriter.append(genericRecord);
            }
            dataFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error writing Avro file.");
        }
    }

    // Method to read Avro file and convert to JSON
    public void readAvroFromFileToJsonFile(File readLocation, File jsonFilePath) {
        DatumReader<GenericRecord> reader = new GenericDatumReader<>();
        try {
            DataFileReader<GenericRecord> dataFileReader = new DataFileReader<>(readLocation, reader);
            DatumWriter<GenericRecord> jsonWriter = new GenericDatumWriter<>(dataFileReader.getSchema());
            Schema schema = dataFileReader.getSchema();
            // Read each Avro record and write as JSON
            OutputStream fos = new FileOutputStream(jsonFilePath);
            JsonEncoder jsonEncoder = EncoderFactory.get().jsonEncoder(schema, fos);
            while (dataFileReader.hasNext()) {
                GenericRecord record = dataFileReader.next();
                System.out.println(record.toString());
                jsonWriter.write(record, jsonEncoder);
                jsonEncoder.flush();
            }
            dataFileReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
