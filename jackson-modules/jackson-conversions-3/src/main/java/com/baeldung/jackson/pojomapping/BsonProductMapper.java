package com.baeldung.jackson.pojomapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.undercouch.bson4jackson.BsonFactory;
import org.bson.BsonBinaryWriter;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.RawBsonDocument;
import org.bson.codecs.BsonDocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.io.BasicOutputBuffer;

import java.io.IOException;

public class BsonProductMapper {

    private final ObjectMapper objectMapper;

    public BsonProductMapper() {
        this.objectMapper = new ObjectMapper(new BsonFactory());
    }

    public byte[] toBytes(Product product) throws JsonProcessingException {
        return objectMapper.writeValueAsBytes(product);
    }

    public Product fromBytes(byte[] bson) throws IOException {
        return objectMapper.readValue(bson, Product.class);
    }

    public Document toDocument(Product product) throws IOException {
        byte[] bytes = toBytes(product);
        RawBsonDocument bsonDoc = new RawBsonDocument(bytes);
        return Document.parse(bsonDoc.toJson());
    }

    public Product fromDocument(Document document) throws IOException {
        BsonDocument bsonDoc = document.toBsonDocument();
        BasicOutputBuffer buffer = new BasicOutputBuffer();
        new BsonDocumentCodec().encode(
          new BsonBinaryWriter(buffer),
          bsonDoc, EncoderContext
            .builder()
            .build()
        );
        return fromBytes(buffer.toByteArray());
    }
}
