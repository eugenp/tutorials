package com.baeldung.libraries.snakeyaml;

import org.junit.Test;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;

import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class JavaToYAMLSerializationUnitTest {

    @Test
    public void whenDumpMap_thenGenerateCorrectYAML() {
        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("name", "Silenthand Olleander");
        data.put("race", "Human");
        data.put("traits", new String[] { "ONE_HAND", "ONE_EYE" });
        Yaml yaml = new Yaml();
        StringWriter writer = new StringWriter();
        yaml.dump(data, writer);
        String expectedYaml = "name: Silenthand Olleander\nrace: Human\ntraits: [ONE_HAND, ONE_EYE]\n";
        assertEquals(expectedYaml, writer.toString());
    }

    @Test
    public void whenDumpACustomType_thenGenerateCorrectYAML() {
        Customer customer = new Customer();
        customer.setAge(45);
        customer.setFirstName("Greg");
        customer.setLastName("McDowell");
        Yaml yaml = new Yaml();
        StringWriter writer = new StringWriter();
        yaml.dump(customer, writer);        
        String expectedYaml = "!!com.baeldung.libraries.snakeyaml.Customer {age: 45, contactDetails: null, firstName: Greg,\n  homeAddress: null, lastName: McDowell}\n";
        assertEquals(expectedYaml, writer.toString());
    }

    @Test
    public void whenDumpAsCustomType_thenGenerateCorrectYAML() {
        Customer customer = new Customer();
        customer.setAge(45);
        customer.setFirstName("Greg");
        customer.setLastName("McDowell");
        Yaml yaml = new Yaml();
        String expectedYaml = "{age: 45, contactDetails: null, firstName: Greg, homeAddress: null, lastName: McDowell}\n";
        assertEquals(expectedYaml, yaml.dumpAs(customer, Tag.MAP, null));
    }

}
