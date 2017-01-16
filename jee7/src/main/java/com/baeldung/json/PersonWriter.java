package com.baeldung.json;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;

public class PersonWriter {
    private Person person;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    
    public PersonWriter(Person person) {
        this.person = person;
    }

    public String write() throws IOException {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
                .add("firstName", person.getFirstName())
                .add("lastName", person.getLastName())
                .add("birthdate", dateFormat.format(person.getBirthdate()));
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                
        for(String email : person.getEmails()) {
            arrayBuilder.add(email);
        }
        
        objectBuilder.add("emails", arrayBuilder);
        
        JsonObject jsonObject = objectBuilder.build();
        
        JsonWriterFactory writerFactory = createWriterFactory();
        
        String jsonString = writeToString(jsonObject, writerFactory);    
        
        return jsonString;
        
    }

    private String writeToString(JsonObject jsonObject, JsonWriterFactory writerFactory) throws IOException {
        String jsonString;
        try(Writer writer = new StringWriter()) {
            writerFactory.createWriter(writer).write(jsonObject);
            jsonString = writer.toString();
        }
        return jsonString;
    }

    private JsonWriterFactory createWriterFactory() {
        Map<String, Boolean> config = new HashMap<>();

        config.put(JsonGenerator.PRETTY_PRINTING, true);
        
        JsonWriterFactory writerFactory = Json.createWriterFactory(config);
        return writerFactory;
    }
}
