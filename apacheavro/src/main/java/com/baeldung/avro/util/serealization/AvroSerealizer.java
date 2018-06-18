package com.baeldung.avro.util.serealization;

import com.baeldung.avro.util.model.AvroHttpRequest;
import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AvroSerealizer {

public byte[] serealizeAvroHttpRequestJSON(AvroHttpRequest request){
    DatumWriter<AvroHttpRequest> writer = new SpecificDatumWriter<>(AvroHttpRequest.class);
    byte[] data = new byte[0];
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    Encoder jsonEncoder = null;
    try {
        jsonEncoder = EncoderFactory.get().jsonEncoder(AvroHttpRequest.getClassSchema(), stream);
        writer.write(request, jsonEncoder);
        jsonEncoder.flush();
        data = stream.toByteArray();
    } catch (IOException e) {
        data =null;
    }
    return data;
}

public byte[] serealizeAvroHttpRequestBinary(AvroHttpRequest request){
    DatumWriter<AvroHttpRequest> writer = new SpecificDatumWriter<>(AvroHttpRequest.class);
    byte[] data = new byte[0];
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    Encoder jsonEncoder = EncoderFactory.get().binaryEncoder(stream,null);
    try {
        writer.write(request, jsonEncoder);
        jsonEncoder.flush();
        data = stream.toByteArray();
    } catch (IOException e) {
        data = null;
    }

    return data;
}

}
