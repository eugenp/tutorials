package org.baeldung.json.schema;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;

public class JSONSchemaTest {

    @Test
    public void validateJSON() {

        JSONObject jsonSchema = new JSONObject(new JSONTokener(JSONSchemaTest.class.getResourceAsStream("/schema.json")));
        JSONObject jsonSubject = new JSONObject(new JSONTokener(JSONSchemaTest.class.getResourceAsStream("/product.json")));

        Schema schema = SchemaLoader.load(jsonSchema);

        try {

            schema.validate(jsonSubject);
        }

        catch (ValidationException e) {

            System.out.println(e.getMessage());
            e.getCausingExceptions().stream().map(ValidationException::getMessage).forEach(System.out::println);
        }
    }
}
