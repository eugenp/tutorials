package com.baeldung.avro.util.serialization;

import com.baeldung.avro.util.model.AvroHttpRequest;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class AvroDeSerializer {

    private static Logger logger = LoggerFactory.getLogger(AvroDeSerializer.class);

    public AvroHttpRequest deSerializeAvroHttpRequestJSON(byte[] data) {
        DatumReader<AvroHttpRequest> reader = new SpecificDatumReader<>(AvroHttpRequest.class);
        Decoder decoder = null;
        try {
            decoder = DecoderFactory.get()
                .jsonDecoder(AvroHttpRequest.getClassSchema(), new String(data));
            return reader.read(null, decoder);
        } catch (IOException e) {
            logger.error("Deserialization error" + e.getMessage());
        }
        return null;
    }

    public AvroHttpRequest deSerializeAvroHttpRequestBinary(byte[] data) {
        DatumReader<AvroHttpRequest> employeeReader = new SpecificDatumReader<>(AvroHttpRequest.class);
        Decoder decoder = DecoderFactory.get()
            .binaryDecoder(data, null);
        try {
            return employeeReader.read(null, decoder);
        } catch (IOException e) {
            logger.error("Deserialization error" + e.getMessage());
        }
        return null;
    }
}
