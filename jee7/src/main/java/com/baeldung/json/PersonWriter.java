package com.baeldung.json;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class PersonWriter {
    private Person person;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    public PersonWriter(Person person) {
        this.person = person;
    }

    public String write() throws IOException {
        JsonObjectBuilder objectBuilder = Json
          .createObjectBuilder()
          .add("firstName", person.getFirstName())
          .add("lastName", person.getLastName())
          .add("birthdate", dateFormat.format(person.getBirthdate()));

        final JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        person.getEmails().forEach(arrayBuilder::add);

        objectBuilder.add("emails", arrayBuilder);

        JsonObject jsonObject = objectBuilder.build();

        JsonWriterFactory writerFactory = createWriterFactory();

        return writeToString(jsonObject, writerFactory);

    }

    private String writeToString(JsonObject jsonObject, JsonWriterFactory writerFactory) throws IOException {
        String jsonString;
        try (Writer writer = new StringWriter()) {
            writerFactory
              .createWriter(writer)
              .write(jsonObject);
            jsonString = writer.toString();
        }
        return jsonString;
    }

    private JsonWriterFactory createWriterFactory() {
        Map<String, Boolean> config = new HashMap<>();

        config.put(JsonGenerator.PRETTY_PRINTING, true);

        return Json.createWriterFactory(config);
    }
}
