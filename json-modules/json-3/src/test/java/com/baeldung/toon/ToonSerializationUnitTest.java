package com.baeldung.toon;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.cedarsoftware.io.JsonIo;
import com.cedarsoftware.io.WriteOptions;
import com.cedarsoftware.io.WriteOptionsBuilder;
import com.cedarsoftware.io.TypeHolder;
import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.EncodingType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ToonSerializationUnitTest {

    // --- Section 4.1: Single Objects ---

    @Test
    void givenPerson_whenSerializedToToon_thenProducesKeyValueFormat() {
        Person person = new Person("Alice", 28, "Engineering");

        String toon = JsonIo.toToon(person, null);

        assertTrue(toon.contains("name: Alice"));
        assertTrue(toon.contains("age: 28"));
        assertTrue(toon.contains("department: Engineering"));
    }

    // --- Section 4.2: Collections - Tabular Format ---

    @Test
    void givenEmployeeList_whenSerializedToToon_thenUsesTabularFormat() {
        List<Employee> employees = Arrays.asList(
            new Employee("Alice Johnson", 28, "Engineering", 95000),
            new Employee("Bob Smith", 34, "Marketing", 78000),
            new Employee("Charlie Brown", 22, "Engineering", 72000)
        );

        String toon = JsonIo.toToon(employees, null);

        assertTrue(toon.contains("[3]{name,age,department,salary}:"));
        assertTrue(toon.contains("Alice Johnson,28,Engineering,95000"));
        assertTrue(toon.contains("Bob Smith,34,Marketing,78000"));
        assertTrue(toon.contains("Charlie Brown,22,Engineering,72000"));
    }

    @Test
    void givenPrettyPrintEnabled_whenSerializedToToon_thenUsesExpandedFormat() {
        List<Employee> employees = Arrays.asList(
            new Employee("Alice Johnson", 28, "Engineering", 95000),
            new Employee("Bob Smith", 34, "Marketing", 78000)
        );

        WriteOptions options = new WriteOptionsBuilder().prettyPrint(true).build();
        String toon = JsonIo.toToon(employees, options);

        assertTrue(toon.contains("name: Alice Johnson"));
        assertTrue(toon.contains("age: 28"));
        assertTrue(toon.contains("department: Engineering"));
        assertTrue(toon.contains("salary: 95000"));
    }

    // --- Section 4.3: Nested Structures ---

    @Test
    void givenNestedStructure_whenSerializedToToon_thenIndentsCorrectly() {
        Map<String, Object> dept1 = new LinkedHashMap<>();
        dept1.put("name", "Engineering");
        dept1.put("members", Arrays.asList(
            new Person("Alice", 28, "Engineering"),
            new Person("Bob", 34, "Engineering")
        ));

        Map<String, Object> company = new LinkedHashMap<>();
        company.put("name", "Acme Corp");
        company.put("founded", 2010);
        company.put("departments", Arrays.asList(dept1));

        String toon = JsonIo.toToon(company, null);

        assertTrue(toon.contains("name: Acme Corp"));
        assertTrue(toon.contains("founded: 2010"));
        assertTrue(toon.contains("departments"));
    }

    @Test
    void givenNestedPersonList_whenSerializedToToon_thenTabularHeaderIncludesAllFields() {
        Map<String, Object> dept = new LinkedHashMap<>();
        dept.put("name", "Engineering");
        dept.put("members", Arrays.asList(
            new Person("Alice", 28, "Engineering"),
            new Person("Bob", 34, "Engineering"),
            new Person("Charlie", 22, "Engineering")
        ));

        Map<String, Object> company = new LinkedHashMap<>();
        company.put("name", "Acme Corp");
        company.put("founded", 2010);
        company.put("departments", Arrays.asList(dept));

        String toon = JsonIo.toToon(company, null);

        assertTrue(toon.contains("{name,age,department}:"),
            "Tabular header should include all 3 Person fields. Actual output:\n" + toon);
    }

    // --- Section 5: Reading TOON Back to Java ---

    @Test
    void givenToonString_whenDeserialized_thenFieldsMatchOriginal() {
        String toon = "name: Alice\nage: 28\ndepartment: Engineering";

        Person person = JsonIo.fromToon(toon, null).asClass(Person.class);

        assertEquals("Alice", person.getName());
        assertEquals(28, person.getAge());
        assertEquals("Engineering", person.getDepartment());
    }

    @Test
    void givenTabularToon_whenDeserializedWithTypeHolder_thenReturnsList() {
        String toon = "[2]{name,age,department}:\n  Alice,28,Engineering\n  Bob,34,Marketing";

        List<Person> people = JsonIo.fromToon(toon, null)
            .asType(new TypeHolder<List<Person>>(){});

        assertEquals(2, people.size());
        assertEquals("Alice", people.get(0).getName());
        assertEquals("Engineering", people.get(0).getDepartment());
        assertEquals("Bob", people.get(1).getName());
        assertEquals("Marketing", people.get(1).getDepartment());
    }

    @Test
    void givenToonString_whenDeserializedToMap_thenContainsExpectedKeys() {
        String toon = "name: Alice\nage: 28\ndepartment: Engineering";

        Map<String, Object> map = JsonIo.fromToonToMaps(toon).asClass(Map.class);

        assertEquals("Alice", map.get("name"));
        assertEquals(28, ((Number) map.get("age")).intValue());
        assertEquals("Engineering", map.get("department"));
    }

    // --- Section 6: Token Efficiency ---

    @Test
    void givenEmployeeList_whenComparedAsJsonAndToon_thenToonUsesFewerTokens() {
        List<Employee> employees = Arrays.asList(
            new Employee("Alice Johnson", 28, "Engineering", 95000),
            new Employee("Bob Smith", 34, "Marketing", 78000),
            new Employee("Charlie Brown", 22, "Engineering", 72000)
        );

        String json = JsonIo.toJson(employees, null);
        String toon = JsonIo.toToon(employees, null);

        EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
        Encoding encoding = registry.getEncoding(EncodingType.O200K_BASE);

        int jsonTokens = encoding.countTokens(json);
        int toonTokens = encoding.countTokens(toon);

        assertTrue(toonTokens < jsonTokens,
            "TOON (" + toonTokens + ") should use fewer tokens than JSON (" + jsonTokens + ")");
    }
}
