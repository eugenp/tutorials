package com.baeldung.apache.avro;

import java.io.File;
import java.io.IOException;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import com.baeldung.apache.avro.generated.Parent;

public class SerializationDeserializationLogic {

    static void serializeParent(Parent parent) throws IOException {
        DatumWriter<Parent> userDatumWriter = new SpecificDatumWriter(Parent.class);

        try (DataFileWriter<Parent> dataFileWriter = new DataFileWriter(userDatumWriter)) {
            dataFileWriter.create(parent.getSchema(), new File("parent.avro"));
            dataFileWriter.append(parent);
        }
    }

    static Parent deserializeParent() throws IOException {
        DatumReader<Parent> userDatumReader = new SpecificDatumReader(Parent.class);

        try (DataFileReader<Parent> dataFileReader = new DataFileReader(new File("parent.avro"), userDatumReader)) {
            return dataFileReader.next();
        }
    }
}
