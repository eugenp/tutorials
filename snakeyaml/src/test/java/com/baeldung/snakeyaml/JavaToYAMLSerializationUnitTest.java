package com.baeldung.snakeyaml;

import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;

import com.baeldung.snakeyaml.model.Customer;

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
        System.out.println(writer.toString());
        
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
        System.out.println(writer.toString());
    }

    @Test
    public void whenDumpAsCustomType_thenGenerateCorrectYAML() {
        Customer customer = new Customer();
        customer.setAge(45);
        customer.setFirstName("Greg");
        customer.setLastName("McDowell");
        Yaml yaml = new Yaml();
        yaml.dumpAs(customer, Tag.MAP, null);
        System.out.println(yaml.dumpAs(customer, Tag.MAP, null));
    }

}
