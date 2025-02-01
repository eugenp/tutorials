package com.baeldung.apache.avro;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.File;
import java.io.IOException;

import com.baeldung.apache.avro.generated.Car;

public class SerializationDeserializationLogic {

    static void serializeCar(Car car) throws IOException {
        DatumWriter<Car> userDatumWriter = new SpecificDatumWriter(Car.class);

        try (DataFileWriter<Car> dataFileWriter = new DataFileWriter(userDatumWriter)) {
            dataFileWriter.create(car.getSchema(), new File("cars.avro"));
            dataFileWriter.append(car);
        }
    }

    static Car deserializeCar() throws IOException {
        DatumReader<Car> userDatumReader = new SpecificDatumReader(Car.class);

        try (DataFileReader<Car> dataFileReader = new DataFileReader(new File("cars.avro"), userDatumReader)) {
            return dataFileReader.next();
        }
    }
}
