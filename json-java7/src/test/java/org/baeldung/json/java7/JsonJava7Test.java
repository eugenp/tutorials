package org.baeldung.json.java7;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.json.Json;
import javax.json.JsonObject;

import org.junit.Before;
import org.junit.Test;

public class JsonJava7Test {
    private Person person;

    @Test
    public void sampleTest() throws IOException {
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("firstName", person.getFirstName())
                .add("lastName", person.getFirstName())
                .build();
        
        String jsonString;
        try(Writer writer = new StringWriter()) {
            Json.createWriter(writer).write(jsonObject);
            jsonString = writer.toString();
        }
        
        assertEquals("{\"firstName\":\"Michael\",\"lastName\":\"Michael\"}", jsonString);
    }

    @Before
    public void createPerson() {
        person = new Person();

        person.setFirstName("Michael");
        person.setLastName("Scott");
    }
}
