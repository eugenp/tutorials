package com.baeldung.apache.avro;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.baeldung.apache.avro.generated.Child;
import com.baeldung.apache.avro.generated.Parent;

public class AvroSchemaWithListOfObjectsTest {

    @Test
    public void whenAvroSchemaWithListOfObjectsIsUsed_thenObjectsAreSuccessfullyCreatedAndSerialized() throws IOException {
        Parent parent = new Parent();
        List<Child> children = new ArrayList();

        Child child1 = new Child();
        child1.setName("Alice");
        children.add(child1);

        Child child2 = new Child();
        child2.setName("Bob");
        children.add(child2);

        parent.setChildren(children);

        SerializationDeserializationLogic.serializeParent(parent);
        Parent deserializedParent = SerializationDeserializationLogic.deserializeParent();

        assertEquals("Alice", deserializedParent.getChildren().get(0).getName());
    }
}
