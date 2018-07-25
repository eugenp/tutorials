package com.baeldung.avro.util.serealization;

import com.baeldung.avro.util.model.AvroHttpRequest;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;

import java.io.IOException;

public class AvroDeSerealizer {

public AvroHttpRequest deSerealizeAvroHttpRequestJSON(byte[] data){
    DatumReader<AvroHttpRequest> reader = new SpecificDatumReader<>(AvroHttpRequest.class);
    Decoder decoder = null;
    try {
        decoder = DecoderFactory.get().jsonDecoder(AvroHttpRequest.getClassSchema(), new String(data));
        return reader.read(null, decoder);
    } catch (IOException e) {
        return null;
    }
}

public AvroHttpRequest deSerealizeAvroHttpRequestBinary(byte[] data){
    DatumReader<AvroHttpRequest> employeeReader = new SpecificDatumReader<>(AvroHttpRequest.class);
    Decoder decoder = DecoderFactory.get().binaryDecoder(data, null);
    try {
        return employeeReader.read(null, decoder);
    } catch (IOException e) {
        return null;
    }
}
}
